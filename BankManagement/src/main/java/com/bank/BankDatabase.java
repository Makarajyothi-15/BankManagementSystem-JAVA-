
package com.bank;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class BankDatabase {
    public static MongoClient connect() {
        String uri = "mongodb://localhost:27017"; 
        MongoClient client = MongoClients.create(uri);
        System.out.println("Connected to MongoDB Successfully!");
        return client;
    }

    // Insert Data Function
    public static void insertCustomer(String name, String accountNumber, double balance) {
        MongoClient client = connect();
        MongoDatabase db = client.getDatabase("BankDB"); // Database Name(BankDB)
        MongoCollection<Document> collection = db.getCollection("customers"); // Collection Name(customers)

        Document customer = new Document("name", name)
                .append("accountNumber", accountNumber)
                .append("balance", balance);

        collection.insertOne(customer);
        System.out.println(" Customer Inserted Successfully!");
        client.close();
    }

    public static void viewCustomers() {
        MongoClient client = connect();
        MongoDatabase db = client.getDatabase("BankDB");
        MongoCollection<Document> collection = db.getCollection("customers");

        for (Document doc : collection.find()) {
            System.out.println(" Name: " + doc.getString("name"));
            System.out.println(" Account Number: " + doc.getString("accountNumber"));

            Object balanceObj = doc.get("balance");
            double balance = 0.0;
            if (balanceObj instanceof Integer) {
                balance = ((Integer) balanceObj).doubleValue();
            } else if (balanceObj instanceof Double) {
                balance = (Double) balanceObj;
            }

            System.out.println("Balance: ₹" + balance);
            System.out.println("--------------------------------------------------");
        }

        client.close();
    }

    public static void updateCustomerBalance(String accountNumber, double newBalance) {
        
        MongoClient client = connect();

        
        MongoDatabase db = client.getDatabase("BankDB");
        MongoCollection<Document> collection = db.getCollection("customers");

        
        Document filter = new Document("accountNumber", accountNumber);
        Document update = new Document("$set", new Document("balance", newBalance));
        collection.updateOne(filter, update);
        System.out.println("Customer Balance Updated Successfully!");
        client.close();
    }

    // Delete All Customers - for testing only!
    public static void deleteAllCustomers() {
        MongoClient client = connect();
        MongoDatabase db = client.getDatabase("BankDB");
        MongoCollection<Document> collection = db.getCollection("customers");

        collection.deleteMany(new Document()); 
        System.out.println("All customer records deleted!");

        client.close();
    }

    public static void deleteCustomer(String accountNumber) {

        MongoClient client = connect();

        
        MongoDatabase db = client.getDatabase("BankDB");
        MongoCollection<Document> collection = db.getCollection("customers");

    
        Document filter = new Document("accountNumber", accountNumber);
        collection.deleteOne(filter);
        System.out.println(" Customer Deleted Successfully!");
        client.close();
    }


    public static void deleteCustomerr(String keyword) {
        MongoClient client = connect();
        MongoDatabase db = client.getDatabase("BankDB");
        MongoCollection<Document> collection = db.getCollection("customers");
    

        Document filter = new Document("$or", java.util.Arrays.asList(
            new Document("name", keyword),
            new Document("accountNumber", keyword)
        ));
    
    
        long deletedCount = collection.deleteOne(filter).getDeletedCount();
    
        if (deletedCount > 0) {
            System.out.println(" Customer Deleted Successfully for: " + keyword);
        } else {
            System.out.println(" Customer Not Found for Deletion: " + keyword);
        }
    
        client.close();
    }
    

    // Search Customer
public static void searchCustomer(String keyword) {
    MongoClient client = connect();
    MongoDatabase db = client.getDatabase("BankDB");
    MongoCollection<Document> collection = db.getCollection("customers");


    Document filter = new Document("$or", java.util.Arrays.asList(
        new Document("name", keyword),
        new Document("accountNumber", keyword)
    ));

    boolean customerFound = false; 

    for (Document doc : collection.find(filter)) {
        customerFound = true;
        System.out.println("Customer Found:");
        System.out.println("Name: " + doc.getString("name"));
        System.out.println("Account Number: " + doc.getString("accountNumber"));

        Object balanceObj = doc.get("balance");
        double balance = 0.0;
        if (balanceObj instanceof Integer) {
            balance = ((Integer) balanceObj).doubleValue();
        } else if (balanceObj instanceof Double) {
            balance = (Double) balanceObj;
        }

        System.out.println("Balance: ₹" + balance);
        System.out.println("--------------------------------------------------");
    }

    
      if (!customerFound) {
        System.out.println("Customer not found with the given keyword: " + keyword);
    }

    client.close();
}

// Deposit
public static void depositAmount(String accountNumber, double amount) {
    MongoClient client = connect();
    MongoDatabase db = client.getDatabase("BankDB");
    MongoCollection<Document> collection = db.getCollection("customers");

    Document filter = new Document("accountNumber", accountNumber);
    Document customer = collection.find(filter).first();

    if (customer != null) {
        double currentBalance = customer.getDouble("balance");
        double newBalance = currentBalance + amount;

        Document update = new Document("$set", new Document("balance", newBalance));
        collection.updateOne(filter, update);

        System.out.println(" Amount Deposited Successfully!");
        System.out.println("New Balance: ₹" + newBalance);
    } else {
        System.out.println(" Customer not found!");
    }

    client.close();
}

// Withdraw
public static void withdrawAmount(String accountNumber, double amount) {
    MongoClient client = connect();
    MongoDatabase db = client.getDatabase("BankDB");
    MongoCollection<Document> collection = db.getCollection("customers");

    Document filter = new Document("accountNumber", accountNumber);
    Document customer = collection.find(filter).first();

    if (customer != null) {
        double currentBalance = customer.getDouble("balance");

        if (amount > currentBalance) {
            System.out.println("Insufficient balance!");
        } else {
            double newBalance = currentBalance - amount;
            Document update = new Document("$set", new Document("balance", newBalance));
            collection.updateOne(filter, update);

            System.out.println("Amount Withdrawn Successfully!");
            System.out.println("New Balance: ₹" + newBalance);
        }
    } else {
        System.out.println("Customer not found!");
    }

    client.close();
}

// Check Balance Function
public static void checkBalance(String accountNumber) {
    MongoClient client = connect();
    MongoDatabase db = client.getDatabase("BankDB");
    MongoCollection<Document> collection = db.getCollection("customers");

    
    Document filter = new Document("accountNumber", accountNumber);

    
    Document customer = collection.find(filter).first();

    if (customer != null) {
        String name = customer.getString("name");
        Object balanceObj = customer.get("balance");
        double balance = (balanceObj instanceof Integer) 
                         ? ((Integer) balanceObj).doubleValue() 
                         : (Double) balanceObj;

        System.out.println("Account Holder: " + name);
        System.out.println("Balance: ₹" + balance);
    } else {
        System.out.println("Account not found!");
    }

    client.close();
}



    public static void main(String[] args) {
        
        deleteAllCustomers();

        insertCustomer("jyo", "12345678900", 15000.0);
        insertCustomer("usha", "9999988888", 5000.0);
        insertCustomer("sasi", "7777766666", 12000.0);

        updateCustomerBalance("12345678900", 7500.00);

        viewCustomers();

        deleteCustomer("12345678900"); // delete panna test
        viewCustomers();

        searchCustomer("jyo");
        deleteCustomerr("jyo");

    }

}
