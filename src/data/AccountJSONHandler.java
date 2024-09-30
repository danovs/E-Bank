package data;
import model.BankAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
public class AccountJSONHandler {

    private static final String FILE_PATH = "accounts.json";

    // Load data from JSON file
    public List<BankAccount> loadBankAccounts() {
        List<BankAccount> accounts = new ArrayList<>();
    try {
        String content = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
        JSONArray jsonArray = new JSONArray(content);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = jsonArray.getJSONObject(i);
            BankAccount bankAccount = new BankAccount();
            bankAccount.setID(object.getInt("id"));
            bankAccount.setBalance(object.getDouble("balance"));
            bankAccount.setAnnualInterestRate(object.getDouble("annualInterestRate"));
            accounts.add(bankAccount);
        }
    } catch (IOException e) {
        System.out.println("Ошибка при чтении JSON файла." + e.getMessage());
    }
    return accounts;
    }

    // Save data to JSON file
    public void saveData(List<BankAccount> accounts) {
        JSONArray jsonArray = new JSONArray();

        for (BankAccount bankAccount : accounts) {
            JSONObject object = new JSONObject();
            object.put("id", bankAccount.getID());
            object.put("balance", bankAccount.getBalance());
            object.put("annualInterestRate", bankAccount.getAnnualInterestRate());
            object.put("dateCreated", bankAccount.getDateCreated().toString());

            jsonArray.put(object);
        }
        try (FileWriter file = new FileWriter(FILE_PATH)) {
            file.write(jsonArray.toString(4));
        }
        catch (IOException e) {
            System.out.println("Ошибка при сохранении JSON файла." + e.getMessage());
        }
    }
}

