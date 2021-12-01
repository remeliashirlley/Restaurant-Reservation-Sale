package restaurant.controller;

import restaurant.model.Item;
import restaurant.model.Reservation;
import restaurant.model.SetPackage;
/**
 * <p>Business logic pertaining to promotions. It involves the create, read, update and delete functionalities.
 * <p>Inherits {@link MenuController} 
 * @author Muhaimin
 *
 */
public class PromoController extends MenuController {
	/**
	 * Get promotion by passed name parameter 
	 * @param itemName
	 * @return item
	 */
	public Item getPromoByName(String itemName) {
		for (Item item : getMenu().getPromotionList()) {
			if (itemName.equalsIgnoreCase(item.getName()) == true) {
				return item;
			}
		}
		return null;
	}
	/**
	 * Add promotional item to the promotional list of package based on passed promotion and promoItem parameters.
	 * @param promotion
	 * @param promoItem
	 */
	public void createAddPromoItem(SetPackage promotion, Item promoItem) {
		promotion.getPromotionalItemList().add(promoItem);
	}
	/**
	 * Create a new promotion based on passed promotion parameter. 
	 * @param promotion
	 */
	public void createPromoItem(Item promotion) {
		getMenu().getPromotionList().add(promotion);
	}
	/**
	 * Add promotional item to the promotion based on passed promotion and promoItem parameters. 
	 * @param promotion
	 * @param promoItem
	 */
	public void updateAddPromoItem(SetPackage promotion,Item promoItem) {
		promotion.getPromotionalItemList().add(promoItem);
	}
	/**
	 * Remove promotional item from promotion based on passed promotion and promoItem parameters.
	 * @param promotion
	 * @param promoItem
	 */
	public void updateRemovePromoItem(SetPackage promotion,Item promoItem) {
		promotion.getPromotionalItemList().remove(promoItem);
	}
	/**
	 * Delete promotion by passed item parameter.
	 * @param item
	 */
	public void deletePromoByName(Item item) {
		getMenu().getPromotionList().remove(item);
	}
	/**
	 * Update item name by passed item and newName parameters. 
	 */
	@Override
	public void updateItemNameByName(Item item, String newName) {
		item.setName(newName);
	}
	/**
	 * Update item description by passed item and newDescription parameters.
	 */
	@Override
	public void updateItemDescriptionByName(Item item, String newDescription) {
		item.setDescription(newDescription);
	}
	/**
	 * Update item price by passed item and newPrice parameters.
	 */
	@Override
	public void updateItemPriceByName(Item item, float newPrice) {
		item.setPrice(newPrice);
	}
}