package com.actionbazaar.image;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;


/**
 * Maintains a cache of the image
 */
@Singleton
public class ImageBean {

	/**
     * Image cache - keyed off of file name (UUID)
     */
	private Map<String, ImageRecord> images = new HashMap<>();
	
	
    /**
     * Default constructor. 
     */
    public ImageBean() {
        // TODO Auto-generated constructor stub
    }
    
    /**
     * Adds the image to the cache
     * @param imageId - image id
     * @param imageRecord - image record
     */
    public void addImage(String imageId, ImageRecord imageRecord) {
    	images.put(imageId, imageRecord);
    }
    
    /**
     * Retrieves the image from the cache
     * @param imageId - image id
     * @return file name
     */
    public ImageRecord getFile(String imageId) {
    	return images.get(imageId);
    }
    
    @PostConstruct
    public void innit() {
    	System.out.println("ImageBean singleton initialized......");
    }

}
