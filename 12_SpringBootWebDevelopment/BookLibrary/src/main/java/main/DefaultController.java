package main;

import main.model.Book;
import main.model.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;

@Controller
public class DefaultController
{
    @Autowired
    private BookRepository bookRepository;

    @Value("${someParameter.value}")
    private Integer someParameter;

    @RequestMapping("/")
    public String index(Model model)
    {
        Iterable<Book> bookIterable = bookRepository.findAll();
        ArrayList<Book> books = new ArrayList<>();
        for(Book book : bookIterable) {
            books.add(book);
        }
        model.addAttribute("books", books);
        model.addAttribute("booksCount", books.size());
        model.addAttribute("someParameter", someParameter);
        return "index";
    }
}