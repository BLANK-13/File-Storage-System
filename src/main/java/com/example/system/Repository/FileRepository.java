package com.example.system.Repository;

import com.example.system.Models.MyFile;
import com.example.system.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FileRepository extends JpaRepository<MyFile, Integer> {

    List<MyFile> findAllByUser(User user);

    List<MyFile> findAllBySizeAfterAndUser(Integer size, User user);

    List<MyFile> findAllBySizeBeforeAndUser(Integer size, User user);


    MyFile findMyFileByFileNameAndUser(String filename, User user);

    MyFile findMyFileByIdAndUser(Integer fileId, User user);

    List<MyFile> getAllByFileTypeContainingIgnoreCaseAndUser(String mediaType, User user);

}
