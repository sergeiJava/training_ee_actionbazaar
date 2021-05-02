package com.actionbazaar.controller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.primefaces.model.file.UploadedFile;

import com.actionbazaar.buslogic.PickListBean;
import com.actionbazaar.image.ImageBean;
import com.actionbazaar.image.ImageRecord;
import com.actionbazaar.model.Category;
import com.actionbazaar.web.PageNavigationEnum;
import com.actionbazaar.web.UserDisplay;

@Named
@ConversationScoped
public class SellController implements Serializable {

	private static final Logger logger = Logger.getLogger("SellController");
	/**
	 * 
	 */
	private static final long serialVersionUID = -6988539244711007465L;

	@Inject
	private Conversation converasation;
	
	private String keywords;
	
	private DualListModel<Category> searchCategories = new DualListModel<>();
	
	/**
	 * Root of the tree
	 */
	private TreeNode root;
	
	/**
	 * Selected Tree nodes
	 */
	private TreeNode selectedNodes[];
	
	@Inject
	private PickListBean pickListBean;
	
	@Inject
	private ImageBean imageBean;
	
	private String image1;
	
	private String image2;
	
	private String title;
	
	/**
	 * HTML description of the item
	 */
	private String description;
	
	/**
	 * Duration in days (gets converted to an actual date)
	 */
	private int duration;
	
	private Double minimumPrice;
	
	/**
	 * User display name
	 */
	@Inject
	private UserDisplay userDisplay;
	
	/**
     * Image folder - note images will be stored in a sub-username folder - access will then be restricted
     * so that other users could never access pre-post pictures (for example to get advance notice of a new bid)
     */
	@Resource(name = "bazaar-images")
	private String imageFolder;
	
	/**
     * Initializes the lists
     */
	@PostConstruct
	public void innit() {
		logger.log(Level.INFO, "SellerController initialized.....");
		List<Category> roots = pickListBean.getCategories();
		root = new DefaultTreeNode("Root", null);
		DefaultTreeNode child;
		for(Category ct : roots) {
			child = new DefaultTreeNode(ct,root);
			recurse(ct, child);
		}
	}
	
	/**
	 * Begin the conversation
	 * @return
	 */
	public String startSellWizard() {
		logger.info("Conversation started...");
		this.converasation.begin();
		return PageNavigationEnum.SELL.toString();
	}
	
	/**
     * Recurses over the nodes building the tree
     * @param category - category
     * @param parent - parent
     */
	protected void recurse(Category category, TreeNode parent) {
		for(Category cat : category.getSubCategories()) {
			TreeNode tn = new DefaultTreeNode(cat, parent);
			recurse(cat, tn);
		}
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public DualListModel<Category> getSearchCategories() {
		return searchCategories;
	}

	public void setSearchCategories(DualListModel<Category> searchCategories) {
		this.searchCategories = searchCategories;
	}

	public TreeNode getCategories() {
		return root;
	}

	public void setCategories(TreeNode root) {
		this.root = root;
	}


	/**
	 * Performs the category search
	 */
	public void performCategorySearch() {
		List<String> split = new LinkedList<>();
		StringTokenizer tokenizer = new StringTokenizer(keywords, " ");
		while(tokenizer.hasMoreElements()) {
			split.add((String)tokenizer.nextElement());
		}
		String splitArray[] = new String[split.size()];
		splitArray = split.toArray(splitArray);
		searchCategories.setSource(pickListBean.findCategories(splitArray));
	}
	
	/**
     * Sets the category selection
     * @param selectedNodes - category selection
     */
	public void setCategorySelection(TreeNode[] selectedNodes) {
		this.selectedNodes = selectedNodes;
	}
	
	/**
     * Returns the category selection
     * @return category selection
     */
	public TreeNode[] getCategorySelection() {
		return selectedNodes;
	}
	
	/**
     * Moves to the next page
     * @return document page
     */
	public String performContinue() {
		if((selectedNodes == null || selectedNodes.length == 0) && searchCategories.getTarget().isEmpty()) {
			FacesContext.getCurrentInstance().addMessage("search", new FacesMessage("At least one category must be selected"));
			return null;
		}
		return PageNavigationEnum.DOCUMENT.toString();
	}

	/**
     * Submits the item for bidding
     * @return home
     */
	public String submitListing() {
		return PageNavigationEnum.HOME.toString();
	}
	public String getImage1() {
		return image1;
	}

	public void uploadImage1(FileUploadEvent image1) {
		this.image1 = save(image1.getFile());
	}

	public String getImage2() {
		return image2;
	}

	public void uploadImage2(FileUploadEvent image2) {
		this.image2 = save(image2.getFile());
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Double getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(Double minimumPrice) {
		this.minimumPrice = minimumPrice;
	}
	

	/**
     * Saves the uploaded file into a folder for the user.
     * @param imageFile - image file to be saved
     * @return image id
     */
	private String save(UploadedFile imageFile) {
		try {
			File saveFld = new File(imageFolder+File.separator+userDisplay.getUser().getUsername());
			if(!saveFld.exists()) {
				if(!saveFld.mkdir()) {
					logger.log(Level.INFO, "Unable to create folder: {0}", saveFld.getAbsolutePath());
				}
			}
			File tmp = File.createTempFile("img", "img");
			IOUtils.copy(imageFile.getInputStream(), new FileOutputStream(tmp));
			File thumbnailImage = new File(saveFld+File.separator+UUID.randomUUID().toString()+".png");
			File fullResolution = new File(saveFld+File.separator+UUID.randomUUID().toString()+".png");
			
			// Create the thumbnail
			BufferedImage image = ImageIO.read(tmp);
			Image thumbnailIm = image.getScaledInstance(310, 210, Image.SCALE_SMOOTH);
			// Convert the thumbnail java.awt.Image into a rendered image which we can save
			BufferedImage thumbnailBi = new BufferedImage(thumbnailIm.getWidth(null), thumbnailIm.getHeight(null), BufferedImage.TYPE_INT_RGB);
			Graphics bg = thumbnailBi.getGraphics();
			bg.drawImage(thumbnailIm, 0, 0, null);
			bg.dispose();
			
			ImageIO.write(thumbnailBi, "png", thumbnailImage);
			// Write out the full resolution image as a thumbnail
			ImageIO.write(image,"png",fullResolution);
			if(!tmp.delete()) {
				logger.log(Level.INFO, "Unable to delete: {0}", tmp.getAbsolutePath());
			}
			String imageID = UUID.randomUUID().toString();
			imageBean.addImage(imageID, new ImageRecord(imageFile.getFileName(), fullResolution.getAbsolutePath(), 
					thumbnailImage.getAbsolutePath(), userDisplay.getUser().getUsername()));
			return imageID;
		} catch (Throwable t) {
			logger.log(Level.SEVERE, "Unable to save the image.", t);
			return null;
		}
	}
	
	
}
