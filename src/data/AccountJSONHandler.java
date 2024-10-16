package data;

import model.BankAccount;
import model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AccountJSONHandler {
    private static final String FILE_PATH = "accounts.json";

    // загрузка данных с json
    public List<BankAccount> loadBankAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                BankAccount bankAccount = new BankAccount();
                bankAccount.setID(object.getInt("id"));
                bankAccount.setName(object.getString("name"));
                bankAccount.setBalance(object.getDouble("balance"));
                bankAccount.setAnnualInterestRate(object.getDouble("annualInterestRate"));
                // загрузка транзакций
                JSONArray transactionArray = object.getJSONArray("transactions");
                for (int j = 0; j < transactionArray.length(); j++) {
                    JSONObject transactionObject = transactionArray.getJSONObject(j);
                    String type = transactionObject.getString("type");
                    double amount = transactionObject.getDouble("amount");
                    String description = transactionObject.getString("description");
                    Transaction transaction = new Transaction(type, amount, description);
                    bankAccount.getTransactions().add(transaction);
                }
                accounts.add(bankAccount);
            }
        } catch (IOException e) {
            System.out.println("Ошибка при чтении JSON файла." + e.getMessage());
        }
        return accounts;
    }

    // сохранение в json
    public void saveData(List<BankAccount> accounts) {
        JSONArray jsonArray = new JSONArray();

        for (BankAccount bankAccount : accounts) {
            JSONObject object = new JSONObject();
            object.put("id", bankAccount.getID());
            object.put("name", bankAccount.getName()); // Save the name
            object.put("balance", bankAccount.getBalance());
            object.put("annualInterestRate", bankAccount.getAnnualInterestRate());
            object.put("dateCreated", bankAccount.getDateCreated().toString());

            // сохранение транзакций
            JSONArray transactionArray = new JSONArray();
            for (Transaction transaction : bankAccount.getTransactions()) {
                JSONObject transactionObject = new JSONObject();
                transactionObject.put("date", transaction.getDate().toString());
                transactionObject.put("type", transaction.getType());
                transactionObject.put("amount", transaction.getAmount());
                transactionObject.put("description", transaction.getDescription());
                transactionArray.put(transactionObject);
            }
            object.put("transactions", transactionArray);

            jsonArray.put(object);
        }
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(4));
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении JSON файла." + e.getMessage());
        }
    }
}
