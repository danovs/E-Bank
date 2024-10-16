import data.AccountJSONHandler;
import model.BankAccount;
import model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final String WELCOME_MESSAGE = "| Добро пожаловать в Е-Банк. |";
    private static final String MENU_HEADER = "|----------------------------|";
    private static final String EXIT_MESSAGE = "| Рады, что были с нами! |";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AccountJSONHandler jsonHandler = new AccountJSONHandler();
        List<BankAccount> accounts = jsonHandler.loadBankAccounts();

        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getUserChoice(scanner);

            switch (choice) {
                case 1:
                    createNewAccount(scanner, jsonHandler, accounts);
                    break;
                case 2:
                    loginAccount(scanner, accounts, jsonHandler);
                    break;
                case 3:
                    System.out.println(MENU_HEADER);
                    System.out.println(EXIT_MESSAGE);
                    System.out.println(MENU_HEADER);
                    jsonHandler.saveData(accounts);
                    running = false;
                    break;
                default:
                    System.out.println("|-----------------------------------|");
                    System.out.println("| Неверный выбор, попробуйте снова. |");
                    System.out.println("|-----------------------------------|");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void displayMainMenu() {
        System.out.println(MENU_HEADER);
        System.out.println(WELCOME_MESSAGE);
        System.out.println(MENU_HEADER);
        System.out.println("| 1. Создать новый аккаунт   |");
        System.out.println("| 2. Войти по ID в аккаунт   |");
        System.out.println("| 3. Выход                   |");
        System.out.println(MENU_HEADER);
        System.out.print("Выберите вариант: ");
    }

    private static int generateNewId(List<BankAccount> accounts) {
        return accounts.stream().mapToInt(BankAccount::getID).max().orElse(-1) + 1;
    }

    private static void createNewAccount(Scanner scanner, AccountJSONHandler accountJSONHandler, List<BankAccount> accounts) {
        int id = generateNewId(accounts);
        System.out.println("|------------------------------------------------------------|");
        System.out.println("| Регистрация                                                |");
        System.out.println("|------------------------------------------------------------|");

        System.out.println("| Присвоен новый ID: " + id + " |");
        System.out.println("|------------------------------------------------------------|");

        String name;
        do {
            System.out.print("Введите имя владельца счета: ");
            name = scanner.nextLine();
        } while (!isValidName(name));

        double balance = getValidBalance(scanner);
        double initialBalanceWithInterest = balance + calculateInterest(balance);

        BankAccount newAccount = new BankAccount(id, name, initialBalanceWithInterest);
        accounts.add(newAccount);
        System.out.println("|------------------------------------------------------------|");
        System.out.println("| Аккаунт создан успешно!                                    |");
        System.out.println("|------------------------------------------------------------|");

        loginAccount(scanner, accounts, accountJSONHandler);
    }

    private static boolean isValidName(String name) {
        return name.matches("[A-Za-zА-Яа-яЁё\\s]+");
    }

    private static double calculateInterest(double amount) {
        return amount * 0.20;
    }

    private static double getValidBalance(Scanner scanner) {
        System.out.print("Введите баланс: ");
        return getDoubleFromUser(scanner, "");
    }

    private static void loginAccount(Scanner scanner, List<BankAccount> accounts, AccountJSONHandler accountJSONHandler) {
        System.out.print("Введите ID аккаунта: ");
        int id = getUserChoice(scanner);

        Optional<BankAccount> existingAccount = accounts.stream()
                .filter(acc -> acc.getID() == id).findFirst();

        if (existingAccount.isPresent()) {
            BankAccount account = existingAccount.get();
            System.out.println("|------------------------------------------------------------|");
            System.out.println("| Добро пожаловать, " + account.getName() + " |");
            System.out.println("|------------------------------------------------------------|");

            boolean loggedIn = true;
            while (loggedIn) {
                displayAccountMenu();
                int choice = getUserChoice(scanner);

                switch (choice) {
                    case 1:
                        System.out.printf("| Текущий баланс: %.2f |\n", account.getBalance());
                        break;
                    case 2:
                        handleWithdrawal(scanner, account, accounts, accountJSONHandler);
                        break;
                    case 3:
                        handleDeposit(scanner, account, accounts, accountJSONHandler);
                        break;
                    case 4:
                        System.out.println(account.displayAccountSummary());
                        break;
                    case 5:
                        displayMaxBalanceAccount(accounts);
                        break;
                    case 6:
                        loggedIn = false;
                        break;
                    default:
                        System.out.println("|-----------------------------------|");
                        System.out.println("| Неверный выбор, попробуйте снова. |");
                        System.out.println("|-----------------------------------|");
                }
            }
        } else {
            System.out.println("Аккаунт с таким ID не найден.");
        }
    }

    private static void displayAccountMenu() {
        System.out.println("|----------------------------|");
        System.out.println("| Основное меню.            |");
        System.out.println("|----------------------------|");
        System.out.println("| 1. Проверить баланс счета. |");
        System.out.println("| 2. Снять со счета.        |");
        System.out.println("| 3. Положить на счет.      |");
        System.out.println("| 4. Показать сводку счета. |");
        System.out.println("| 5. Показать наибольший баланс. |");
        System.out.println("| 6. Выход.                 |");
        System.out.println("|----------------------------|");
        System.out.print("Выберите вариант: ");
    }

    private static void handleWithdrawal(Scanner scanner, BankAccount account, List<BankAccount> accounts, AccountJSONHandler accountJSONHandler) {
        System.out.print("Введите сумму, которую хотите снять с банковского счета: ");
        double withdrawAmount = getDoubleFromUser(scanner, "");
        if (withdrawAmount <= account.getBalance()) {
            account.withdraw(withdrawAmount);
            accountJSONHandler.saveData(accounts);
            System.out.printf("Снятие %.2f выполнено. Ваш баланс: %.2f\n", withdrawAmount, account.getBalance());
        } else {
            System.out.println("Недостаточно средств для снятия.");
        }
    }

    private static void handleDeposit(Scanner scanner, BankAccount account, List<BankAccount> accounts, AccountJSONHandler accountJSONHandler) {
        System.out.print("Введите сумму, которую хотите положить на банковский счет: ");
        double depositAmount = getDoubleFromUser(scanner, "");

        double totalDepositWithInterest = depositAmount + calculateInterest(depositAmount);
        account.deposit(totalDepositWithInterest);
        accountJSONHandler.saveData(accounts);
        System.out.printf("Внесение %.2f выполнено. Ваш баланс: %.2f\n", totalDepositWithInterest, account.getBalance());
    }

    private static void handleInterest(Scanner scanner, BankAccount account, List<BankAccount> accounts, AccountJSONHandler accountJSONHandler) {
        account.applyMonthlyInterest();
        accountJSONHandler.saveData(accounts);
        System.out.printf("Начислены проценты. Ваш новый баланс: %.2f\n", account.getBalance());
    }

    private static double getDoubleFromUser(Scanner scanner, String prompt) {
        String input;
        double value = 0;
        boolean validInput = false;

        while (!validInput) {
            if (!prompt.isEmpty()) {
                System.out.print(prompt);
            }
            input = scanner.next().replace(",", ".");
            try {
                value = Double.parseDouble(input);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите корректное число.");
            }
        }
        return value;
    }

    private static int getUserChoice(Scanner scanner) {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Ошибка: введите число.");
                scanner.next();
            }
        }
    }

    private static void displayMaxBalanceAccount(List<BankAccount> accounts) {
        Optional<BankAccount> maxBalanceAccount = accounts.stream()
                .max((acc1, acc2) -> Double.compare(acc1.getBalance(), acc2.getBalance()));

        if (maxBalanceAccount.isPresent()) {
            BankAccount account = maxBalanceAccount.get();
            System.out.println("|------------------------------------------------------------|");
            System.out.printf("| Аккаунт с наибольшим балансом: %s (ID: %d) |\n", account.getName(), account.getID());
            System.out.printf("| Баланс: %.2f |\n", account.getBalance());
            System.out.println("|------------------------------------------------------------|");
        } else {
            System.out.println("| Список аккаунтов пуст. |");
        }
    }
}