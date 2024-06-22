public class Bid {
    
    public boolean Buy(Product product, double bidPrice){
        if(bidPrice > product.getBidPrice()){
            product.setBidPrice(bidPrice);
            product.setHighestBidder(LoginManager.getActiveUser());
            return true;
        }
        else{
            return false;
        }
    }
}
