import java.io.*;
import java.util.StringTokenizer;

public class Interface {
    public static void main(String[] args) {
        /*
        Driver Code
        Runs till exit command
         */
        String input = args[0];
        BSTDictionary dict = new BSTDictionary();

        if (args.length > 1) {
            System.out.println("FILE" + args[0]);
        }

        try {loader(input, dict);}
        catch (IOException | DictionaryException e) {
        System.err.println("Error reading input: " + e.getMessage());
        }

        StringReader kbIn = new StringReader();
        while (true) {
            String command = kbIn.read("").trim();
            if (command.equalsIgnoreCase("exit")) {
                break;
            }
            commandProcession(command, dict);

        }



    }

    /**
     * Processes the commands, calls the relevant methods accordingly
     * @param command String
     * @param dict BSTDictionary
     */
    private static void commandProcession(String command, BSTDictionary dict) {
        String[] tokens = command.split(" ");
        if (tokens.length < 1) {
            System.out.println("Invalid command");
            return;
        }
        String word = tokens[0].toLowerCase();
        String label = (tokens.length > 1) ? tokens[1].toLowerCase() : null;

        switch (word) {
            case "define":
                if (label != null) {
                    define(label, dict);
                }
                break;
            case "translate":
                if (label != null) {
                    translate(label, dict);
                }
                break;
            case "sound":
                if (label != null) {
                    try {sound(label, dict);}
                    catch (MultimediaException e) {System.err.println("Error sound: " + e.getMessage());}
                }
                break;
            case "play":
                if (label != null) {
                    try {play(label, dict);}
                    catch (MultimediaException e) {System.err.println("Error play: " + e.getMessage());}
                }
                break;
            case "say":
                if (label != null) {
                    try {say(label, dict);}
                    catch (MultimediaException e) {System.err.println("Error say: " + e.getMessage());}
                }
                break;
            case "show":
                if (label != null) {
                    try {show(label, dict);}
                    catch (MultimediaException e) {System.err.println("Error show: " + e.getMessage());}
                }
                break;
            case "animate":
                if (label != null) {
                    try {animate(label, dict);}
                    catch (MultimediaException e) {System.err.println("Error animate: " + e.getMessage());}
                }
                break;
            case "browse":
                if (label != null) {
                    try {browse(label, dict);}
                    catch (Exception e) {System.err.println("Error browse: " + e.getMessage());}
                }
                break;
            case "delete":
                if (tokens.length == 3) {
                    try {
                        int type = Integer.parseInt(tokens[2]);
                        delete(label, type, dict);
                    } catch (NumberFormatException e) {System.out.println("Invalid command.");}
                }
                else {System.out.println("Invalid command.");}
                break;
            case "add":
                if (tokens.length >= 4) {
                    try {
                        int type = Integer.parseInt(tokens[2]);
                        String data = tokens[3];
                        add(label, type, data, dict);
                    } catch (NumberFormatException e) {System.out.println("Invalid command.");}
                }
                else {System.out.println("Invalid command.");}
                break;
            case "list":
                list(label, dict);
                break;
            case "first":
                first(dict);
                break;
            case "last":
                last(dict);
                break;
            case "exit":
                System.exit(0); // Terminates the program
                break;
            default:
                System.out.println("Invalid command.");
        }
    }

    /**
     * Loads the input to dictionary records
     *
     * @param input file
     * @param dict BSTDictionary
     * @throws IOException
     * @throws DictionaryException
     */
    private static void loader(String input, BSTDictionary dict) throws IOException, DictionaryException {
        String label;
        String type;
        BufferedReader in = new BufferedReader(new FileReader(input));

        while ((label = in.readLine()) != null) {
            label = label.toLowerCase();
            type = in.readLine();

            Record rec = parser(label, type);
            dict.put(rec);
        }

        in.close();
    }

    /**
     * Parses the file
     *
     * @param label String
     * @param typeData String
     * @return Record
     */
    private static Record parser(String label, String typeData) {
    int type = 1; // default type
    String data = typeData;

    // Used to parse
    StringTokenizer tkner = new StringTokenizer(typeData, " ");
    char fChar = typeData.charAt(0);

    // first character/file extension
    switch (fChar) {
        case '-':
            type = 3; // sound
            data = typeData.substring(1).trim();
            break;
        case '+':
            type = 4; // music
            data = typeData.substring(1).trim();
            break;
        case '*':
            type = 5; // voice
            data = typeData.substring(1).trim();
            break;
        case '/':
            type = 2; // french
            data = typeData.substring(1).trim();
            break;
        default:
            // if file extension or plain text
            if (tkner.countTokens() == 1) {
                // if one token
                String token = tkner.nextToken();
                // file extensions
                if (token.endsWith(".wav") || token.endsWith(".mid")) {type = 3;} // sound
                else if (token.endsWith(".jpg")) {type = 6;} // image
                else if (token.endsWith(".gif")) {type = 7;} // animated image file
                else if (token.endsWith(".html")) {type = 8;}
                else {type = 1;}
                data = token;
            }
            else {data = typeData;}
            break;
    }

    // return rec with the parsed information
    return new Record(new Key(label, type), data);
    }

    /**
     * Handles the define command
     * @param label String
     * @param dict BSTDictionary
     */
    private static void define(String label, BSTDictionary dict) {
        Key key = new Key(label, 1);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("The word " + label +  " is not in the dictionary ");
        }
        else {
            System.out.println(rec.getDataItem());
        }
    }

    /**
     * Handles the tranlsate command
     * @param label String
     * @param dict BSTDictionary
     */
    private static void translate(String label, BSTDictionary dict) {
        Key key = new Key(label, 2);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no definition for the word " + label);
        }
        else {
            System.out.println(rec.getDataItem());
        }
    }

    /**
     * Handles the sound command, uses SoundPlayer
     * @param label String
     * @param dict BSTDictionary
     */
    private static void sound(String label, BSTDictionary dict) throws MultimediaException {
      Key key = new Key(label, 3);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no sound file for " + label);
        }
        else {
            SoundPlayer sp = new SoundPlayer();
            sp.play(rec.getDataItem());
        }
    }

    /**
     * Handles the play command, uses SoundPlayer
     * @param label String
     * @param dict BSTDictionary
     */
    private static void play(String label, BSTDictionary dict) throws MultimediaException {
        Key key = new Key(label, 4);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no music file for " + label);
        }
        else {
            SoundPlayer sp = new SoundPlayer();
            sp.play(rec.getDataItem());
        }
    }

    /**
     * Handles the say command, uses SoundPlayer
     * @param label String
     * @param dict BSTDictionary
     */
    private static void say(String label, BSTDictionary dict) throws MultimediaException {
        Key key = new Key(label, 5);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no voice file for " + label);
        }
        else {
            SoundPlayer sp = new SoundPlayer();
            sp.play(rec.getDataItem());
        }
    }

    /**
     * Handles the show command, uses PictureViewer
     * @param label String
     * @param dict BSTDictionary
     */
    private static void show(String label, BSTDictionary dict) throws MultimediaException {
        Key key = new Key(label, 6);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no image file for " + label);
        }
        else {
            PictureViewer pv = new PictureViewer();
            pv.show(rec.getDataItem());
        }
    }

    /**
     * Handles the animate command, uses PictureViewer
     * @param label String
     * @param dict BSTDictionary
     */
    private static void animate(String label, BSTDictionary dict) throws MultimediaException {
        Key key = new Key(label, 7);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no animated image file for " + label);
        }
        else {
            PictureViewer pv = new PictureViewer();
            pv.show(rec.getDataItem());
        }
    }

    /**
     * Handles the browse command, uses ShowHTML
     * @param label String
     * @param dict BSTDictionary
     */
    private static void browse(String label, BSTDictionary dict) throws Exception{
        Key key = new Key(label, 8);
        Record rec = dict.get(key);
        if (rec == null) {
            System.out.println("There is no webpage called " + label);
        }
        else {
            ShowHTML sh = new ShowHTML();
            sh.show(rec.getDataItem());
        }
    }

    /**
     * Uses the remove method from BST implementation of BSTDictionary ADT
     * @param label String
     * @param type int
     * @param dict BSTDictionary
     */
    private static void delete(String label, int type, BSTDictionary dict) {
        Key key = new Key(label, type);
        try {
            dict.remove(key);
        }
        catch (Exception ignored) {
            System.out.println("No record in the ordered dictionary has key (" + label + "," + type + ")");
        }
    }

    /**
     * Uses the put method from BST implementation of BSTDictionary ADT
     * @param label String
     * @param type int
     * @param data String
     * @param dict BSTDictionary
     */
    private static void add(String label, int type, String data, BSTDictionary dict) {
        Key key = new Key(label, type);
        try {dict.put(new Record(key, data));}
        catch (Exception ignored) {
            System.out.println("A record with the given key (" + label +  type + ") is already in the ordered dictionary");
        }
    }

    /**
     * Traverses through the dictionary using smallest and successor methods, to find records with matching prefixes
     * @param prefix String input
     * @param dict BSTDictionary
     */
    private static void list(String prefix, BSTDictionary dict) {
    // Traverse by finding the smallest node, then finding successor successively
    Record rec = dict.smallest();
    StringBuilder res = new StringBuilder();

    boolean found = false;

    while (rec != null) {
        if (rec.getKey().getLabel().startsWith(prefix)) {
            found = true;
            res.append(rec.getKey().getLabel()).append(",");
        }
        // Move to the successor in the dictionary
        rec = dict.successor(rec.getKey());
    }

    res = new StringBuilder(res.substring(0, res.length() - 2)).append(".");
    if (found) {System.out.println(res);}
    else {System.out.println("No label attributes in the ordered dictionary start with prefix " + prefix);}

    }

    /**
     * Finds the smallest record
     * @param dict BSTDictionary
     */
    private static void first(BSTDictionary dict) {
        StringBuilder res = new StringBuilder();
        Record rec = dict.smallest();

        if (rec == null) {return;}

        res.append(rec.getKey().getLabel()).append(",");
        res.append(rec.getKey().getType()).append(",");
        res.append(rec.getDataItem()).append(" ");;

        System.out.println(res);
    }

    /**
     * Finds the largest record
     * @param dict BSTDictionary
     */
    private static void last(BSTDictionary dict) {
        StringBuilder res = new StringBuilder();
        Record rec = dict.largest();

        if (rec == null) {return;}

        res.append(rec.getKey().getLabel()).append(",");
        res.append(rec.getKey().getType()).append(",");
        res.append(rec.getDataItem()).append(" ");

        System.out.println(res);
    }

}
