package eu.ohim.sp.common;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Goal which touches a timestamp file.
 *
 * @goal copy-resources
 * 
 * @phase process-sources
 */
public class SPCopyResourcesMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     * @parameter
     * @required
     */
    private File inputDirectory;
    
    
    /**
     * Location of the file.
     * @parameter
     * @required
     */
    private File outputDirectory;
    
    /**
     * Location of the file.
     * @parameter
     */
    private String office;
    
    

    public void execute()
        throws MojoExecutionException
    {
    	
  
        File f = outputDirectory;

        if ( !f.exists() )
        {
            f.mkdirs();
        }

        
        try{
            if(inputDirectory!=null && inputDirectory.isDirectory()){
            	File[] files=inputDirectory.listFiles();
            	if(files!=null && files.length>0){
            		for(File file:files){
            			if(file.isFile()){
            				//if file, then copy it
            	    		//Use bytes stream to support all file types
            			
            	    		InputStream in = new FileInputStream(file);
            	    		File newFile=new File(outputDirectory,file.getName());
            	    	    OutputStream out = new FileOutputStream(newFile);
            	    	    byte[] buffer = new byte[1024];
            	    	    
                	        int length;
                	        //copy the file content in bytes 
                	        while ((length = in.read(buffer)) > 0){
                	    	   out.write(buffer, 0, length);
                	        }
             
                	        in.close();
                	        out.close();
            	 
            			}
            			
            		}
            		for(File file:files){
                        if(office!=null && !office.equals("") && file.getName().equals(office)){
                            if(file.isDirectory()){
                                System.out.println("is Directory "+file.getName());
                                File[] files_subDirectory=file.listFiles();
                                System.out.println("Reading subdirectory "+file.getName());
                                if (files_subDirectory!=null) {
                                    for(File office_file:files_subDirectory){
                                        if(office_file.isFile()){
                                            InputStream in = new FileInputStream(office_file);
                                            File newFile=new File(outputDirectory,office_file.getName());
                                            OutputStream out = new FileOutputStream(newFile,true);
                                            byte[] buffer = new byte[1024];

                                            int length;
                                            //copy the file content in bytes
                                            while ((length = in.read(buffer)) > 0){
                                                out.write(buffer, 0, length);
                                            }

                                            in.close();
                                            out.close();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        catch(Exception ee){
        	throw new MojoExecutionException(ee.getMessage());
        }
    }
}
