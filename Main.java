package org.example;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//Разбить первую строку на лексемы (используя разделители из  второй строки),
// определить в ней вещественные числа.  Числа записать в новый отдельные массив.
// Среди лексем не являющихся числами, найти даты (ДД\ММ\ГГ). Добавить в строку случайное число до
// вещественного числа  или в конец строки(если нет) .  Подстроку (с самой маленькой длиной),
// начинающуюся символом лат. алфавита и заканчивающуюся цифрой - удалить из строки.
// Все результаты сформировать в строки и вывести.
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("out.txt"));
        BufferedReader bufferedReader = new BufferedReader(new FileReader("in.txt"));
        String stroka = bufferedReader.readLine();
        String razd = bufferedReader.readLine();


        StringTokenizer tokenizer = new StringTokenizer(stroka, razd);
        List<String> tokens = new ArrayList<>();
        while (tokenizer.hasMoreTokens()) {
            tokens.add(tokenizer.nextToken());
        }

        List<Double> numbers = new ArrayList<>();
        List<Date> dates = new ArrayList<>();
        bufferedWriter.write("Лексемы:\n");

        String firstNumberToken = null;
        for (String token : tokens) {
            boolean isDouble = true;
            try {
                numbers.add(Double.parseDouble(token));
                if (firstNumberToken == null)
                    firstNumberToken = token;
            } catch (Exception ignore) {
                isDouble = false;
            }
            if (!isDouble) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy\\MM\\dd");
                    dates.add(simpleDateFormat.parse(token));
                } catch (Exception ignore) {
                }
            }
            bufferedWriter.write(token + "\n");
        }

        if (!numbers.isEmpty()) {
            bufferedWriter.write("Числа:\n");
            for (Double number : numbers) {
                bufferedWriter.write(number + "\n");
            }
        }

        if (!dates.isEmpty()) {
            bufferedWriter.write("Даты: \n");
            for (Date date : dates) {
                bufferedWriter.write(date + "\n");
            }
        }

        Pattern pattern = Pattern.compile("[a-zA-Z][^\\d]*\\d");
        Matcher matcher = pattern.matcher(stroka);
        String minSubstring = null;
        while (matcher.find()) {
            String found = matcher.group();
            if (minSubstring == null || found.length() < minSubstring.length()) {
                minSubstring = found;
            }
            
        }
        if (minSubstring != null) {
            stroka = stroka.replaceFirst(Pattern.quote(minSubstring), "");
        }
        bufferedWriter.write("Строка после удаления подстроки:\n" + stroka + "\n");


        bufferedWriter.write("Строка со случайным числом:\n");
        if (firstNumberToken != null){
            bufferedWriter.write(stroka.replaceFirst(firstNumberToken,Math.random() +firstNumberToken));
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(stroka).append(Math.random());
            bufferedWriter.write(stringBuffer.toString());
        }

        bufferedReader.close();
        bufferedWriter.close();
    }
}
