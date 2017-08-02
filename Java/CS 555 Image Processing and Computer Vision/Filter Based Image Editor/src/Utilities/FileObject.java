/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To chFileObject template file, choose Tools | Templates
 * and open the template iFileObjecttor.
 */

package Utilities;

import java.io.File;

/**
 *
 * @author Matthew Lai
 */
public class FileObject {
    
    final File file;
    final String extension;
    public FileObject(File file, String ext)
    {
        this.file = file;
        this.extension = ext;
    }
    
    public File getFile()
    {
        return file;
    }
    
    public String getExt()
    {
        return extension;
    }
    
}
