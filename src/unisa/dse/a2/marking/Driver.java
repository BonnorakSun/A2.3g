package unisa.dse.a2.marking;

import unisa.dse.a2.students.DSEList;
import unisa.dse.a2.students.ListedCompany;
import unisa.dse.a2.students.SecuritiesExchange;
import unisa.dse.a2.students.StockBroker;
import unisa.dse.a2.students.Trade;
import unisa.dse.a2.students.UntradedCompanyException;

public class Driver {

	SecuritiesExchange exchange;

	public static void main(String[] args) {

		Driver d = new Driver();

	}

	public Driver() {
		
		exchange = new SecuritiesExchange("ASX");
		
		StockBroker harry = new StockBroker("Honest Harry Broking");
		exchange.addBroker(harry);
		
		StockBroker dave = new StockBroker("Dodge Dave Broking");
		exchange.addBroker(dave);
		
		ListedCompany dall = new ListedCompany("DALL", "Dall Computers Limited", 1000);
		ListedCompany gs = new ListedCompany("GME", "GameStonk", 100);
		ListedCompany tisla = new ListedCompany("TSLA", "Tisla Intergalactic", 9000);
		ListedCompany wiki = new ListedCompany("WIKI", "Wikipedia", 0);
		
		exchange.addCompany(dall);
		exchange.addCompany(gs);
		exchange.addCompany(tisla);
		
		harry.placeOrder(new Trade(harry, "DALL", 100));
		dave.placeOrder(new Trade(dave, "TSLA", 100));
		
		try
		{
			exchange.processTradeRound();
			
			for (int i = 0; i < exchange.announcements.size(); i++)
			{
				System.out.println(exchange.announcements.get(i));
			}
		} 
		catch (UntradedCompanyException x)
		{
			System.out.println(x.getMessage());
		}
		
		System.out.println("\n--- Test 1: Adding duplicate company ---");
		ListedCompany repeatCompany = new ListedCompany("DALL", "Duplicate Dall", 500);
		boolean added = exchange.addCompany(repeatCompany);
		System.out.println("Attempt to add duplicate company (should be false): " + added);

		System.out.println("\n--- Test 2: Watchlist prioritization ---");
		harry.addWatchlist("TSLA");
		harry.placeOrder(new Trade(harry, "TSLA", 200)); // now on watchList
		Trade nextTrade = harry.getNextTrade();
		System.out.println("Trade retrieved (should be prioritized due to watchlist): " + nextTrade.getCompanyCode());

		System.out.println("\n--- Test 3: Watchlist equality ---");
		StockBroker anotherHarry = new StockBroker("Honest Harry Broking");
		anotherHarry.addWatchlist("TSLA");
		System.out.println("Harry equals AnotherHarry (should be true if names match): " + harry.equals(anotherHarry));
		
		System.out.println("\n--- Test 4: Trading unlisted company ---");
		try {
		    dave.placeOrder(new Trade(dave, "WIKI", 0));  // WIKI was not added to exchange
		    exchange.processTradeRound(); // should throw exception
		} catch (UntradedCompanyException e) {
		    System.out.println("Caught expected UntradedCompanyException: " + e.getMessage());
		}
		
		System.out.println("\n--- Test 5: Trade created-time comparison ---");
		Trade t1 = new Trade(harry, "DALL", 50);
		Trade t2 = new Trade(harry, "DALL", 50);
		System.out.println("CompareTo (should be -1 or 1 based on creation): " + t1.compareTo(t2));
		
		System.out.println("\n--- Test 6: No trades ---");
		StockBroker emptyBroker = new StockBroker("Empty Broker");
		System.out.println("Pending trades: " + emptyBroker.getPendingTradeCount());
		System.out.println("Next trade (should be null): " + emptyBroker.getNextTrade());
		
		System.out.println("\n--- Test 7: DSEList manual test ---");
		DSEList list = new DSEList();
		list.add("AAPL");
		list.add("GOOG");
		list.add("MSFT");
		System.out.println("List: " + list.toString());
		list.remove("GOOG");
		System.out.println("After removal: " + list.toString());
		System.out.println("Contains MSFT? " + list.contains("MSFT"));

	}

}
