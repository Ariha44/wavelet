import java.io.IOException;
import java.net.URI;

class Handler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    private String[] stringArray = new String[100];

    public String handleRequest(URI url) {
        if (url.getPath().equals("/add")) {
            String s = url.getQuery().split("=")[1]; // get the string to add from the query parameter
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i] == null) { // find the first available index in the array
                    stringArray[i] = s; // add the string to the array
                    return String.format("Added string: %s", s);
                }
            }
            return "Array is full!"; // return an error message if the array is already full
        } else if (url.getPath().equals("/search")) {
            String searchSubstring = url.getQuery().split("=")[1]; // get the search substring from the query parameter
            String matchingStrings = ""; // initialize a string to store the matching strings
            for (int i = 0; i < stringArray.length; i++) {
                if (stringArray[i] != null && stringArray[i].contains(searchSubstring)) {
                    if (!matchingStrings.isEmpty()) {
                        matchingStrings += ", "; // add a comma and space separator if there are already matching strings
                    }
                    matchingStrings += stringArray[i]; // add the matching string to the result string
                }
            }
            return matchingStrings.isEmpty() ? "No matches found." : matchingStrings; // return the matching strings or an error message if none found
        } else {
            return "404 Not Found!";
        }
    }
}

class StringServer {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
