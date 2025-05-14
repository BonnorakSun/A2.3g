package unisa.dse.a2.students;

import java.util.HashMap;
import java.util.Scanner;
import unisa.dse.a2.interfaces.ListGeneric;


public class SecuritiesExchange {

	/**
	 * Exchange name
	 */
	private String name;
	
	public String getName() {
		return name;
	}
	
	/**
	 * List of brokers on this exchange
	 */
	public ListGeneric<StockBroker> brokers;
	
	/**
	 * List of announcements of each trade processed
	 */
	public ListGeneric<String> announcements;
	
	/**
	 * HashMap storing the companies, stored based on their company code as the key
	 */
	public HashMap<String, ListedCompany> companies;

	/**
	 * Initialises the exchange ready to handle brokers, announcements, and companies
	 * @param name
	 */
	public SecuritiesExchange(String name){
		this.name = name;
		brokers = new DSEListGeneric<>(); // assuming DSEListGeneric<T> implements ListGeneric<T>
		announcements = new DSEListGeneric<>();
		companies = new HashMap<>();
	}
	
	/**
	 * Adds the given company to the list of listed companies on the exchange
	 * @param company
	 * @return true if the company was added, false if it was not
	 */
	public boolean addCompany(ListedCompany company){
		if (company == null || companies.containsKey(company.getCode())) {
			return false;
		}
		companies.put(company.getCode(), company);
		return true;
	}

	/**
	 * Adds the given broke to the list of brokers on the exchange
	 * @param company
	 */
	public boolean addBroker(StockBroker broker){
		if (broker == null) {
			return false;
		}
		for(int i = 0; i < brokers.size(); i++) {
			if (brokers.get(i).equals(broker)) {
				return false; // avoid duplicates 
			}
		}
		brokers.add(broker);
		return true;
	}
	
	/**
	 * Process the next trade provided by each broker, processing brokers starting from index 0 through to the end
	 * 
	 * If the exchange has three brokers, each with trades in their queue, then three trades will processed, one from each broker.
	 * 
	 * If a broker has no pending trades, that broker is skipped
	 * 
	 * Each processed trade should also make a formal announcement of the trade to the announcements list in the form a string:
	 * "Trade: QUANTITY COMPANY_CODE @ PRICE_BEFORE_TRADE via BROKERNAME", 
	 * e.g. "Trade: 100 DALL @ 99 via Honest Harry Broking" for a sale of 100 DALL shares if they were valued at $99
	 * Price shown should be the price of the trade BEFORE it's processed. Each trade should add its announcement at 
	 * the end of the announcements list
	 * 
	 * @return The number of successful trades completed across all brokers
	 * @throws UntradedCompanyException when traded company is not listed on this exchange
	 */
	public int processTradeRound() throws UntradedCompanyException{
		
		int tradeCount = 0;
		// Loop through brokers and process each trade in their queue
		for(int i = 0; i < brokers.size(); i++) {
			StockBroker broker = brokers.get(i);
			Trade trade = broker.getNextTrade(); // Retrieve the next trade from the broker 
			
			if(trade == null) continue; // If no trade, skip to next broker
			
			String code = trade.getCompanyCode(); // Get the company code from the trade
			int quantity = trade.getShareQuantity(); // Get the quantity of shares from the trade
			
			ListedCompany company = companies.get(code); // Get the listed company by company code
			if(company == null) {
				throw new UntradedCompanyException("Company" + code + "is not listed on this exchange.");
			}
			
			int priceBefore = company.getCurrentPrice(); // Get the price before the trade
			company.processTrade(quantity); // Process the trade, passing the quantity 
			
			// Create an announcement about the trade
			String announcement = "Trade: " + quantity + " " + code + " @ " + priceBefore + " via " + broker.getName();
			announcements.add(announcement); // add the announcement to the list
			tradeCount++; // Increment the trade count
		}
		return tradeCount; // Return the number of trades processed 
	}
	
	public int runCommandLineExchange(Scanner sc){
		
		int rounds = 0; // Count the number of successful trade rounds
		
		// Loop until the user enters "quit"
		while (sc.hasNextLine()) {
			String command = sc.nextLine().trim().toLowerCase(); // Read and normalize the command
			if (command.equals("trade")) {
				try {
					int trades = processTradeRound(); // Attempt to process one trade per broker 
					System.out.println("Processed " + trades + " trade(s).");
					rounds++; // Increment round count after successful processing 
				}catch (UntradedCompanyException e) {
					// If a trade involves an unlisted company, report error
					System.out.println("Error: " + e.getMessage());
				}
			}else if (command.equals("quit")) {
				break; // Exit the loop and method if "quit" is entered
			}else {
				// Handle any unrecognized command
				System.out.println("Unknown command. Type 'trade' or 'quit'. ");
			}
		}
		return rounds; // Return the number of rounds (i.e., successful trade processing sessions)
	}
}
