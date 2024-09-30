import data.AccountJSONHandler;
import model.BankAccount;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountJSONHandler jsonHandler = new AccountJSONHandler();
        List<BankAccount> accounts = jsonHandler.loadBankAccounts();

        boolean running = true;
        while (running) {
            System.out.println("Добро пожаловать в Е-Банк.");
            System.out.println("\n1. Создать новый аккаунт");
            System.out.println("\n2. Войти по ID в аккаунт");
            System.out.println("\n3. Выход");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    createNewAccount(scanner, jsonHandler, accounts);
                    break;
                case 2:
                    loginAccount(scanner, accounts);
                    break;
                case 3:
                    System.out.println("Рады, что были с нами");
                    jsonHandler.saveData(accounts);
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор, попробуйте снова");
            }
            System.out.println();
        }
        scanner.close();
    }
    private static int generateNewId(List<BankAccount> accounts) {
        return accounts.stream().mapToInt(BankAccount::getID).max().orElse(0) + 1;
    }

    private static void createNewAccount(Scanner scanner, AccountJSONHandler accountJSONHandler, List<BankAccount> accounts) {

        System.out.println("Регистрация");
        int id = generateNewId(accounts);
        System.out.println("Присвоен новый ID: " + id);

        System.out.print("Введите начальный баланс: ");
        double balance = scanner.nextDouble();

        System.out.print("Введите годовую процентную ставку (например, 0.05 для 5%): ");
        double annualInterestRate = getDoubleFromUser(scanner);

        BankAccount newAccount = new BankAccount(id, balance, annualInterestRate);
        accounts.add(newAccount);

        System.out.println("Аккаунт создан успешно!");
        accountJSONHandler.saveData(accounts);

    }
    private static void loginAccount(Scanner scanner, List<BankAccount> accounts) {
        System.out.print("Введите ID аккаунта: ");
        int id = scanner.nextInt();

        Optional<BankAccount> existingAccount = accounts.stream()
                .filter(acc -> acc.getID() == id).findFirst();

        if (existingAccount.isPresent()) {
            BankAccount account = existingAccount.get();
            System.out.println("Добро пожаловать, ID: " + account.getID());
            System.out.println("Текущий баланс: " + account.getBalance());
            System.out.println("Годовая процентная ставка: " + account.getAnnualInterestRate());
        } else {
            System.out.println("Аккаунт с таким ID не найден.");
        }
    }
    private static double getDoubleFromUser(Scanner scanner) {
        String input;
        double value = 0;
        boolean validInput = false;

        while (!validInput) {
            System.out.print("Введите значение (используйте запятую для дробной части): ");
            input = scanner.nextLine();

            // Замена запятой на точку для корректного парсинга
            input = input.replace(",", ".");

            try {
                value = Double.parseDouble(input);
                validInput = true;  // Если парсинг успешен, выходим из цикла
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }
        return value;
    }

}