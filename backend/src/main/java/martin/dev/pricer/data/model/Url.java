package martin.dev.pricer.data.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
public class Url extends BaseEntity {

    private String url;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "url_category",
            joinColumns = {@JoinColumn(name = "url_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories;

    public Url() {
    }

    public Url(String url, Store store, Status status) {
        this.url = url;
        this.store = store;
        this.status = status;
    }

    public boolean isReadyToScrape() {
        return this.status.getStatus().equals("Ready") && (this.checkedAt == null || this.checkedAt.isBefore(LocalDateTime.now().minusHours(2)));
    }

    @Override
    public String toString() {
        return "Url{" +
                "url='" + url + '\'' +
                ", checkedAt=" + checkedAt +
                ", store=" + store +
                ", status=" + status +
                '}';
    }
}
