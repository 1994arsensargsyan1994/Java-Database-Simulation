package databaseSimulation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class JavaDatabases {
    private static final File SERVER_DB = new File("/home/arsen/Desktop/serverDB");

    public static void main(String[] args) {
        new JavaDatabases().commands();

    }

    void commands() {
        Scanner scan = new Scanner(System.in);
        String command = "";
        while (!command.equalsIgnoreCase("exit")) {
            command = scan.nextLine();
            selectCommandDB(command);
        }

    }

    void selectCommandDB(String commandGeneral) {
        String[] commandArray = commandGeneral.split(" ");
        String commandShow = commandArray[0];
        if (commandShow.equals("exit")){
            System.exit(0);
        }
        switch (commandShow) {
            case "CREATE": {
                if (commandArray.length == 3) {
                    String command = commandArray[1];
                    if (command.equals("DATABASE")) {
                        String name = commandArray[2];
                        createDB(name);
                        break;
                    } else
                        System.out.println("wrong command");
                    break;
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            case "SHOW": {
                String command = commandArray[1];
                if (commandArray.length == 2) {
                    if (command.equals("DATABASES")) {
                        showDatabases(command);
                        break;
                    } else
                        System.out.println("an correct command");
                    break;
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            case "USE": {
                String nameDb = commandArray[1];
                if (commandArray.length == 2) {
                    useDb(nameDb);
                    break;
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            default: {
                System.out.println("wrong command or the last create db  and use it");
            }
        }
    }


    void useDb(String nameDb) {
        File[] files = SERVER_DB.listFiles();
        if (files == null) {
            System.out.println("not db in this server");
            return;
        }
        for (File file : files) {
            if (file.isDirectory()) {
                if (file.getName().equals(nameDb)) {
                    jobThisDB(file.getName());
                    return;
                }
            }
        }
        System.out.println("not that name database");
    }

    private void jobThisDB(String directName) {
        System.out.println("you are  use " + directName + " database");
        Scanner scanner = new Scanner(System.in);
        String command = "";
        while (!command.equalsIgnoreCase("exit")) {
            command = scanner.nextLine();
            selectCommandTable(command, directName);
        }
    }


    private void selectCommandTable(String commandGeneral, String directName) {
        String[] commandArray = commandGeneral.split(" ");
        String path = SERVER_DB + File.separator + directName;
        String command = commandArray[0];
        int length = commandArray.length;
        switch (command) {
            case "exit": {
                if (length == 1) {
                    System.exit(0);
                }
            }
            case "CREATE": {
                if (length == 4) {
                    String parameters = commandArray[3];
                    if (commandArray[1].equals("TABLE")) {
                        File file = new File(path, commandArray[2]);
                        try {
                            boolean is = file.createNewFile();
                            System.out.println(is);
                        } catch (IOException e) {
                            System.out.println("test");
                        }
                        createTable(file, parameters);
                        break;
                    } else {
                        System.out.println("wrong command");
                    }
                } else {
                    System.out.println("wrong table name");
                    break;
                }
            }
            case "PREVIOUS": {
                if (length == 1) {
                    commands();
                    break;
                } else {
                    System.out.println("wrong command");
                }
                break;

            }
            case "SHOW": {
                if (length == 2) {
                    if (commandArray[1].equals("TABLES")) {
                        showTables(path);
                        break;
                    } else if (commandArray[1].equals("DATABASES")) {
                        System.out.println("SELECT < PREVIOUS> for this command");
                    } else {
                        System.out.println("wrong command");
                    }
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            default: {
                System.out.println("wrong command");
            }
        }
    }

    private void createTable(File file, String parameters) {
        String temp = parameters.substring(1, parameters.length() - 1);
        String[] parametersArray = temp.split(",");
        try (BufferedWriter bWrit = new BufferedWriter(new FileWriter(file))) {
            bWrit.write("  ");
            for (String s : parametersArray) {
                bWrit.write(s + "\t");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showTables(String path) {
        File database = new File(path);
        File[] tables = database.listFiles();
        if (tables == null) {
            System.out.println("is database  not table");
            return;
        }
        for (File table : tables) {
            System.out.println(table.getName());
        }
    }

    void showDatabases(String command) {
        if (command.equals("DATABASES")) {
            File[] databases = SERVER_DB.listFiles();
            if (databases == null) {
                System.out.println("is your SERVER no databases");
                return;
            }
            for (File file : databases) {
                if (file.isDirectory()) {
                    System.out.println(file.getName());
                }
            }
        }
    }

    void createDB(String name) {
        File database = new File(SERVER_DB, name);
        database.mkdir();
    }
}
