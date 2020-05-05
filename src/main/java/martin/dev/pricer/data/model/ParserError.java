package martin.dev.pricer.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ParserError extends BaseEntity {

    private int stepNumber;
    private String parserOperation;
    private String url;

    @ManyToOne
    @JoinColumn(name = "url_id")
    private Url urlObject;

}
