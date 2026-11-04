package se.yrgo.inlamning1.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import se.yrgo.inlamning1.domain.Book;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BookController {

    List<Book> allBooks = new ArrayList<>();

    public BookController() {
        allBooks.add(new Book("Drakarna", "Hasse", "Fantasi"));
        allBooks.add(new Book("Kärlek", "Lasse", "Drama"));
        allBooks.add(new Book("Kampen", "Frasse", "Sport"));
        allBooks.add(new Book("Matlagning", "Tasse", "Recept"));
    }

    @GetMapping("/home")
    public String home(Model model) {
        Date dateAndTime = new Date();
        model.addAttribute("welcomeMessage",
                "Välkommen till bokklubben, tiden är:");
        model.addAttribute("dateAndTime", dateAndTime);
        return "home";
    }

    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("allBooks", allBooks);
        return "booklist";
    }

    @GetMapping("/genre")
    public String genre(@RequestParam(required = false) String type, Model model) {
        List<Book> filteredBooks = allBooks;

        if (type != null && !type.isBlank()) {
            String selectedGenre = type.trim();

            filteredBooks = allBooks.stream()
                    .filter(book -> book.getGenre().equalsIgnoreCase(selectedGenre))
                    .toList();

            if (filteredBooks.isEmpty()) {
                model.addAttribute("warningMessage",
                        "No books found for genre: " + selectedGenre);
            }
        }

        model.addAttribute("allBooks", filteredBooks);
        model.addAttribute("type", type);
        return "genre";
    }
}
