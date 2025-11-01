package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidacaoRegex {
    public static boolean verificarEmail(String email) {
        Pattern pattern = Pattern.compile("^[\\w.-]+@([\\w-]+\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String verificarCnpj(String cnpj) {

        String numeros = cnpj.replaceAll("\\D", "");
        Pattern pattern = Pattern.compile("\\d{14}");
        if (pattern.matcher(numeros).matches()) {
            return numeros;
        } else {
            return null;
        }
    }

    public static String verificarCep(String cep) {

        String numeros = cep.replaceAll("\\D", "");
        Pattern pattern = Pattern.compile("\\d{8}");
        if (pattern.matcher(numeros).matches()) {
            return numeros;
        } else {
            return null;
        }
    }

    public static boolean verificarSenha(String senha) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9!@#$%^&*()_\\-+=<>?{}\\[\\]~]{8,}$");
        return pattern.matcher(senha).matches();
    }
}

