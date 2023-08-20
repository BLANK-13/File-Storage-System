package com.example.system.Repository;

import com.example.system.Models.MyFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<MyFile, Integer> {

    List<MyFile> findAllByFileOwnerId(Integer id);

    List<MyFile> findAllBySizeAfterAndFileOwnerId(Long size, Integer ownerID);

    List<MyFile> findAllBySizeBeforeAndFileOwnerId(Long size, Integer ownerID);


    MyFile findMyFileByFileNameAndFileOwnerId(String fileName, Integer ownerID);

    MyFile findMyFileByIdAndFileOwnerId(Integer fileID, Integer ownerID);


    List<MyFile> getAllByFileTypeContainingIgnoreCaseAndFileOwnerId(String mediaType, Integer ownerID);

}
