package com.example.system.Services;


import com.example.system.ApiUtils.FileExceptions.FileDoesNotExistException;
import com.example.system.ApiUtils.FileExceptions.YouHaveNoFilesException;
import com.example.system.ApiUtils.WrongTokenException;
import com.example.system.Models.User;
import com.example.system.Repository.FileRepository;
import com.example.system.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.system.Models.MyFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service

@RequiredArgsConstructor
public class FileService {


    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    private final String SERVER_FILES_FOLDER = "C:/Users/isaud/IdeaProjects/System/src/main/resources/user_files/";

    public record FileInfoRecord(MediaType mediaType, byte[] data) {
    }

    public String uploadFile(MultipartFile file, String userToken) throws IOException, FileDoesNotExistException {

        if (file.isEmpty()) throw new FileDoesNotExistException();


        ////// we'll make a directory for each user in our Server and store whatever we need to retrieve x file in the database, this way is way faster than storing the files in the db as BLOB.
        User user = userRepository.findUserByUserToken(userToken);
        if (user == null) throw new WrongTokenException();

        Integer userID = user.getId();
        String fileLocation = SERVER_FILES_FOLDER + userID + "/" + file.getOriginalFilename();

        Files.createDirectories(Paths.get(fileLocation));


        file.transferTo(new File(fileLocation));

        MyFile uploadFile = new MyFile();
        uploadFile.setFileName(file.getOriginalFilename());
        uploadFile.setFileType(file.getContentType());
        uploadFile.setSize(file.getSize() >> 20); //// converting bytes to mbs by bit shifting instead of dividing
        uploadFile.setFileOwnerId(userID);

        fileRepository.save(uploadFile);

        return "File named " + file.getOriginalFilename() + " uploaded successfully !";
    }


    public List<MyFile> getMyFiles(String userToken) throws WrongTokenException, YouHaveNoFilesException {

        ///// doing this way allows us to prevent any unwanted access to any user's files since this token is generated and given everytime the user login to their account.
        User filesOwner = userRepository.findUserByUserToken(userToken);

        if (filesOwner == null) throw new WrongTokenException();

        List<MyFile> userFilesList = fileRepository.findAllByFileOwnerId(filesOwner.getId());
        if (userFilesList.isEmpty()) throw new YouHaveNoFilesException();

        return userFilesList;
    }

    public FileInfoRecord downloadFileById(String userToken, Integer fileID) throws IOException, WrongTokenException, FileDoesNotExistException {


        ///// doing this way allows us to prevent any unwanted access to any user's files since this token is generated and given everytime the user login to their account.
        User user = userRepository.findUserByUserToken(userToken);

        if (user == null) throw new WrongTokenException();


        MyFile downloadFile = fileRepository.findMyFileByIdAndFileOwnerId(fileID, user.getId());

        if (downloadFile == null) throw new FileDoesNotExistException();

        String downloadFilePath = SERVER_FILES_FOLDER + user.getId() + "/" + downloadFile.getFileName();

        byte[] file = Files.readAllBytes(new File(downloadFilePath).toPath());

        return new FileInfoRecord(MediaType.valueOf(downloadFile.getFileType()), file);

    }

    public FileInfoRecord downloadFileByName(String userToken, String fileName) throws IOException, FileDoesNotExistException, WrongTokenException {


        ///// doing this way allows us to prevent any unwanted access to any user's files since this token is generated and given everytime the user login to their account.
        User user = userRepository.findUserByUserToken(userToken);

        if (user == null) throw new WrongTokenException();

        MyFile downloadFile = fileRepository.findMyFileByFileNameAndFileOwnerId(fileName, user.getId());

        if (downloadFile == null) throw new FileDoesNotExistException();

        String downloadFilePath = SERVER_FILES_FOLDER + user.getId() + "/" + downloadFile.getFileName();

        byte[] file = Files.readAllBytes(new File(downloadFilePath).toPath());


        return new FileInfoRecord(MediaType.valueOf(downloadFile.getFileType()), file);

    }

    public List<MyFile> getMyFilesBiggerThan(String userToken, Long size) throws WrongTokenException, YouHaveNoFilesException {

        ///// doing this way allows us to prevent any unwanted access to any user's files since this token is generated and given everytime the user login to their account.
        User filesOwner = userRepository.findUserByUserToken(userToken);

        if (filesOwner == null) throw new WrongTokenException();

        List<MyFile> userFilesList = fileRepository.findAllBySizeAfterAndFileOwnerId(size, filesOwner.getId());
        if (userFilesList.isEmpty()) throw new YouHaveNoFilesException();

        return userFilesList;
    }

}
