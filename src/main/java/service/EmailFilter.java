package service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

public class EmailFilter {

    public EmailFilter (InputStream input, PrintStream output) {
        Map<String, String> emails = new HashMap<>();
        Map<String, String> usersMap = new HashMap<>();
        Map<String, String> users = new HashMap<>();
        List<String> tokens = new LinkedList<>();
        new StdIn().ask(input).stream()
                .map(str -> Arrays.asList(str.split(",|->"))).forEach(tokens::addAll);
        String[] user = new String[1];
        tokens.stream().map(String::trim)
                .forEach(x -> {
                    if (!x.contains("@")) {
                        user[0] = x;
                        usersMap.put(x, x);
                    } else {
                        emails.merge(x, user[0], (o, n) -> {
                            if (Integer.parseInt(o.substring(4)) < Integer.parseInt(n.substring(4))) { // сравниваем userX < userY
                                usersMap.put(n, o);  // добавляем в таблицу соответствие userY, userX
                                user[0] = o;
                                return o;
                            } else {
                                usersMap.put(o, n);
                                return n;
                            }
                        });

                    }
                });
        emails.forEach((key1, value1) -> users.merge(usersMap.get(value1), key1, (o, n) -> o + ", " + n));
        users.forEach((key, value) -> output.println(key + "->" + value));
    }
}
