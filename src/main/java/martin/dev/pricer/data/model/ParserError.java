package martin.dev.pricer.data.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import martin.dev.pricer.scraper.Parser;
import martin.dev.pricer.scraper.ParserException;
import org.jsoup.nodes.Element;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Document
@Data
public class ParserError extends BaseEntity {

    private LocalDateTime dateTime;
    private Parser parser;
    private String message;
    private Element element;

    public ParserError(LocalDateTime dateTime, Parser parser, String message, Element element) {
        this.dateTime = dateTime;
        this.parser = parser;
        this.message = message;
        this.element = element;
    }

    public ParserError(ParserException e) {
        this.dateTime = LocalDateTime.now();
        this.parser = e.getParser();
        this.message = e.getMessage();
        this.element = e.getElement();
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Parser getParser() {
        return parser;
    }

    public String getMessage() {
        return message;
    }

    public Element getElement() {
        return element;
    }
}
