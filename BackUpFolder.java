/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackUpFolder {

/*copy a given file into a given folder. This checks whether it is a directory or not and then call relevent method to copy that file or directory*/

    public void copy(File sourceLocation, File targetLocation) throws IOException {
        if (sourceLocation.isDirectory()) {
            copyDirectory(sourceLocation, targetLocation);
        } else {
            copyFile(sourceLocation, targetLocation);
        }
    }

/*if the given File is a director, calls this*/
    private void copyDirectory(File source, File target) throws IOException {
        if (!target.exists()) {
            target.mkdir();
        }

        for (String f : source.list()) {
            copy(new File(source, f), new File(target, f));
        }
    }

/*if the given File is not a directory calls this*/
    private void copyFile(File source, File target) throws IOException {
        try (
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(target)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }
    
    /*rename a file inside source folder and also rename it in target(copied) folder*/

    public boolean renameFiles(File fileInSource, File target, String newName) {
        boolean status2 = false;
        File fileInTarget = null;
        if (!fileInSource.isDirectory()) {
            File newFile = new File(rearangePath(fileInSource.getParent() + "/" + newName));
            boolean status = fileInSource.renameTo(newFile);
            System.out.println("status " + status);
            if (status) {
                for (String f : target.list()) {

                    if (f.equals(fileInSource.getName())) {

                        fileInTarget = new File(rearangePath(target.getPath() + "/" + f)); //path that is comming from target.getPath() is out of the default way of writing paths in java
                    }
                }
                File newFile2 = new File(rearangePath(fileInTarget.getParent() + "/" + newName));
                status2 = fileInTarget.renameTo(newFile2);
            }
        }
        return status2;
    }

  /*delete a file inside source folder and also delete it in target(copied) folder*/
    public boolean deleteFiles(File fileInSource, File target) {
        boolean status2 = false;
        File fileInTarget = null;
        boolean status = fileInSource.delete();
        if (status) {
            for (String f : target.list()) {

                if (f.equals(fileInSource.getName())) {

                    fileInTarget = new File(rearangePath(target.getPath() + "/" + f));
                }
            }
            status2 = fileInTarget.delete();
        }
        return status2;
    }
    
    /*replace '\\' that is given from target.getPath() by '/'*/
    private String rearangePath(String path) {
        return path.replace("\\", "/");
    }
}
