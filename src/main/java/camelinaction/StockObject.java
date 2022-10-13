package camelinaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "record")
@XmlAccessorType(XmlAccessType.FIELD)
public class StockObject {


	
	private String ticker;
	private String sector;
	private String bidPrice;
	private String bidQuantity;
	private String askPrice;
	private String askQuantity;
	
	public StockObject() {
		
	}
	public StockObject(String ticker, String sector, String bidPrice, String bidQuantity, String askPrice, String askQuantity) {
		this.ticker = ticker;
		this.sector = sector;
		this.bidPrice = bidPrice;
		this.bidQuantity = bidQuantity;
		this.askPrice = askPrice;
		this.askQuantity = askQuantity;
		
	}
	
	public String getTicker() {
		return ticker;
	}
	
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public String getSector() {
		return sector;
	}
	
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public String getBidPrice() {
		return bidPrice;
	}
	
	public void setBidPrice(String bp) {
		bidPrice = bp;
	}
	
	public String getBidQuantity() {
		return bidQuantity;
	}
	
	public void setBidQuantity(String bq) {
		this.bidQuantity = bq;
	}
	
	public String getAskPrice() {
		return askPrice;
	}
	
	public void setAskPrice(String ap) {
		this.askPrice = ap;
	}
	
	public String getAskQuantity() {
		return askQuantity;
	}
	
	public void setAskQuantity(String aq) {
		this.askQuantity = aq;
	}
	
}