package org.example;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        Scanner scanner = new Scanner(System.in);
        int opcion=7;

        // Repetir el menú hasta que el usuario ingrese la opción 7
        do {
            main.mostrarMensaje(); // Mostrar el menú al usuario

            try {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1:
                        main.conversionMoneda("USD", "ARS");
                        break;
                    case 2:
                        main.conversionMoneda("ARS", "USD");
                        break;
                    case 3:
                        main.conversionMoneda("USD", "BRL");
                        break;
                    case 4:
                        main.conversionMoneda("BRL", "USD");
                        break;
                    case 5:
                        main.conversionMoneda("USD", "COP");
                        break;
                    case 6:
                        main.conversionMoneda("COP", "USD");
                        break;
                    case 7:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (opcion != 7); // Salir del bucle cuando el usuario ingrese la opción 7

        scanner.close(); // Cierra el scanner al finalizar


}


    public void conversionMoneda(String monedaOrigen, String monedaDestino) throws IOException {
        // Obtener la cotización de la API
        String respuestaAPI = consumirAPI(monedaOrigen);

        // Parsear la respuesta JSON
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = parser.parse(respuestaAPI).getAsJsonObject();
        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

        // Obtener la tasa de conversión de moneda
        double tasaConversion = conversionRates.get(monedaDestino).getAsDouble();

        // Mostrar el resultado al usuario
        System.out.println("La tasa de conversión de " + monedaOrigen + " a " + monedaDestino + " es: " + tasaConversion);
    }

    public String consumirAPI(String moneda) throws IOException {
        String url_str = "https://v6.exchangerate-api.com/v6/37cf4de00662ea7471d77c85/latest/" + moneda;
        URL url = new URL(url_str);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();
        InputStreamReader reader = new InputStreamReader((InputStream) request.getContent());
        StringBuilder responseBuilder = new StringBuilder();
        int character;
        while ((character = reader.read()) != -1) {
            responseBuilder.append((char) character);
        }
        return responseBuilder.toString();
    }

    void mostrarMensaje () {
        System.out.println("*******************************************");
        System.out.println("Sea bienvenido/a al Conversor de Moneda =)");
        System.out.println("1) Dolar =>> Peso Argentino");
        System.out.println("2) Peso Argentino =>> Dolar");
        System.out.println("3) Dolar =>> Real brasileño");
        System.out.println("4) Real brasileño =>> Doalr");
        System.out.println("5) Dolar ==> Peso colombiano");
        System.out.println("6) Peso colombiano ==> Dolar");
        System.out.println("7) Salir");
        System.out.println("Eliga una opcion valida: ");
        System.out.println("*******************************************");
    }
}


