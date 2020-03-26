package databaseSimulation;

import java.io.*;
import java.util.*;

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
        int length = commandArray.length;
        if (commandShow.equals("exit")) {
            System.exit(0);
        }
        switch (commandShow) {
            case "CREATE": {
                if (length == 3) {
                    String command = commandArray[1];
                    if (command.equals("DATABASE")) {
                        String name = commandArray[2];
                        createDB(name);
                        System.out.println("you create " + name + " database");
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
                if (length == 2) {
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
                if (length == 2) {
                    useDb(nameDb);
                    break;
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            case "DROP": {
                if (length == 3 && commandArray[1].equals("DATABASE")) {
                    String nameDB = commandArray[2];
                    dropDB(nameDB);
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
                    System.out.println("you dan previous");
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
            case "DROP": {
                if (length == 3) {
                    String nameTable = commandArray[2];
                    if (commandArray[1].equals("TABLE")) {
                        dropTable(path, nameTable);
                        break;
                    } else if (commandArray[1].equals("DATABASE")) {
                        System.out.println("SELECT < PREVIOUS> for this command");
                        break;
                    }
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            case "INSERT": {
                if (length == 5) {
                    insertInto(commandArray, path);
                } else {
                    System.out.println("wrong command");
                }
                break;
            }
            case "SELECT": {
                if (length == 4) {
                    getSelectFromTable(commandArray, path);
//                } else if (length > 4) {
//                    if (commandArray[4].equals("WHERE")) {
//                        getSelectFromTableWhere(commandArray, path);
//                    } else System.out.println("wrong command");
                } else
                    System.out.println("wrong command");
                break;
            }
            default: {
                System.out.println("wrong command");
            }
        }

    }

    private void getSelectFromTableWhere(String[] commandArray, String path) {
        File database = new File(path);
        File[] tables = database.listFiles();
        if (tables == null) {
            System.out.println("not any tables");
            return;
        }
        String[] parameterList = commandArray[1].split(",");
        String nameTable = commandArray[3];
        String str;
        String[] parameterArray = methodReadFirstLine(tables, nameTable);
        for (int i = 0; i < parameterList.length; i++) {
            for (int j = 0; j < parameterArray.length; j++) {
                if (parameterList[i].equals(parameterArray[j])) {
                    // TODO: 26.03.20
                }
            }
        }
    }


    private void insertInto(String[] commandArray, String path) {
        String nameTable = commandArray[2];
        String temp = commandArray[4].substring(1, commandArray[4].length() - 1);
        String[] values = temp.split(",");
        StringBuilder sBu = new StringBuilder();
        if (commandArray[1].equals("INTO") && commandArray[3].equals("VALUES")) {
            File database = new File(path);
            File[] tables = database.listFiles();
            if (tables == null) {
                System.out.println("not any tables");
                return;
            }
            for (File table : tables) {
                if (table.getName().equals(nameTable)) {
                    try (BufferedWriter bWrit = new BufferedWriter(new FileWriter(table, true))) {
                        bWrit.newLine();
                        for (String s : values) {
                            bWrit.write(s + "\t");
                        }
                        System.out.println("ok");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }
            System.out.println("not that name table");
        } else {
            System.out.println("wrong command");
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

    private void createTable(File file, String parameters) {
        String temp = parameters.substring(1, parameters.length() - 1);
        String[] parametersArray = temp.split(",");
        try (BufferedWriter bWrit = new BufferedWriter(new FileWriter(file))) {
            for (String s : parametersArray) {
                bWrit.write(s + "\t");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void dropDB(String nameDB) {
        File database = new File(SERVER_DB + File.separator + nameDB);
        if (database.isDirectory() && database.exists()) {
            database.delete();
            System.out.println("you drop " + nameDB + " database");
        } else {
            System.out.println("not that database");
        }
    }

    void dropTable(String path, String nameTable) {
        File database = new File(path);
        File[] tables = database.listFiles();
        if (tables == null) {
            System.out.println("not any tables");
            return;
        }
        for (File table : tables) {
            if (table.getName().equals(nameTable)) {
                table.delete();
                System.out.println("you drop " + nameTable + " table");
                return;
            }
        }
        System.out.println("not that name table");
    }

    private void getSelectFromTable(String[] commandArray, String path) {
        File database = new File(path);
        File[] tables = database.listFiles();
        if (tables == null) {
            System.out.println("not any tables");
            return;
        }
        if (commandArray[1].equals("*") && commandArray[2].equals("FROM")) {
            getSelectFromTableAll(commandArray, tables);
        } else if (!commandArray[1].equals("*")) {
            getSelectFromTableByParameter(tables, commandArray);

        } else {
            System.out.println("wrong command");
        }
    }

    private void getSelectFromTableByParameter(File[] tables, String[] commandArray) {
        String nameTable = commandArray[3];
        String[] parameterList = commandArray[1].split(",");
        StringBuilder sBu = new StringBuilder();
        for (File table : tables) {
            if (table.getName().equals(nameTable)) {
                try (BufferedReader bRid = new BufferedReader(new FileReader(table))) {
                    String str = bRid.readLine().trim();
                    String[] parameterArray = str.split("\t");
                    for (int i = 0; i < parameterList.length; i++) {
                        for (int j = 0; j < parameterArray.length; j++) {
                            if (parameterList[i].equals(parameterArray[j])) {
                                sBu = methodReadSelectParameter(table, parameterArray, j);
                                System.out.println(sBu.toString());
                                break;
                            }

                        }

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("not that table");

    }

    private String[] methodReadFirstLine(File[] tables, String nameTable) {
        String[] parameterArray = null;
        for (File table : tables) {
            if (table.getName().equals(nameTable)) {
                try (BufferedReader bRid = new BufferedReader(new FileReader(table))) {
                    String str = bRid.readLine().trim();
                    parameterArray = str.split("\t");
                } catch (IOException e) {
                    System.out.println();
                }
            } else {
                System.out.println("not this table");
            }
        }
        return parameterArray;
    }

    private StringBuilder methodReadSelectParameter(File table, String[] value, int i) throws IOException {
        StringBuilder sBu = new StringBuilder();
        String str;
        // sBu.append(value[i] + "\n");
        try (BufferedReader bRid = new BufferedReader(new FileReader(table))) {
            while ((str = bRid.readLine()) != null) {
                str = str.trim();
                String[] temp = str.split("\t");
                sBu.append(temp[i]);
                sBu.append("\n");
            }
        }
        return sBu;
    }

    private void getSelectFromTableAll(String[] commandArray, File[] tables) {
        String nameTable = commandArray[3];
        StringBuilder sBu = new StringBuilder();
        for (File table : tables) {
            if (table.getName().equals(nameTable)) {
                try (BufferedReader bRid = new BufferedReader(new FileReader(table))) {
                    String str;
                    while ((str = bRid.readLine()) != null) {
                        sBu.append(str);
                        sBu.append("\n");
                    }
                    System.out.println(sBu.toString());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        System.out.println("not that table");
    }
}
