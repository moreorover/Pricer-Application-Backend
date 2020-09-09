package martin.dev.pricer.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Job extends BaseEntity {

    private LocalDateTime startDateTime;
    private LocalDateTime finishDateTime;
    private String exitMessage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "url_id", nullable = false)
    private Url url;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

}
