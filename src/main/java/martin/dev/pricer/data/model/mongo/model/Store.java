package martin.dev.pricer.data.model.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@Document
public class Store extends BaseEntity {

    private String name;
    private String baseUrl;
    private String logo;

    private Set<Url> urls;

    @Transient
    private Set<Url> urlsToScrape;

    @PersistenceConstructor
    public Store(String name, String baseUrl, String logo, Set<Url> urls) {
        this.name = name;
        this.baseUrl = baseUrl;
        this.logo = logo;
        this.urls = urls;
    }

    public boolean filterUrlsToScrape() {
        setUrlsToScrape(
                this.getUrls().stream()
                        .filter(Url::isReadyToScrape)
                        .collect(Collectors.toSet())
        );

        return this.urlsToScrape.size() > 0;
    }
}
