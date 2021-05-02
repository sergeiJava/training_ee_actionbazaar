package com.actionbazaar.image;

import java.io.Serializable;

/**
 * Image Recorder
 * @author test
 *
 */
public class ImageRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1817407688423315473L;

	private String originalName;
	
	private String fullResolutionFile;
	
	private String thumbnailFile;
	
	private String username;
	
	/**
	 * Creates a new ImageRecord
     * @param originalName - original name of the file
     * @param fullResolutionFile - fullResolutionFile
     * @param thumbnailFile - thumbnail image
     * @param username - username
	 */
	public ImageRecord(String originalName, String fullResolutionFile, String thumbnailFile, String username) {
		this.originalName = originalName;
		this.fullResolutionFile = fullResolutionFile;
		this.thumbnailFile = thumbnailFile;
		this.username = username;
	}

	/**
	 * Get Original name
	 * @return originale name
	 */
	public String getOriginalName() {
		return originalName;
	}

	/**
	 * Return Full Resolution File (currently on disk)
	 * @return full Resolution File
	 */
	public String getFullResolutionFile() {
		return fullResolutionFile;
	}

	/**
	 * Return the thumbnail file
	 * @return
	 */
	public String getThumbnailFile() {
		return thumbnailFile;
	}

	/**
	 * Returns the username of file uploader
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	
}
