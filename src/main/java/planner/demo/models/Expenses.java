package planner.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Expenses {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double paid;
    private Double owes;
    private Double difference;


    public Expenses(Long id, Double paid, Double owes, Double difference) {
        this.id = id;
        this.paid = paid;
        this.owes = owes;
        this.difference = difference;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }

    public Double getOwes() {
        return owes;
    }

    public void setOwes(Double owes) {
        this.owes = owes;
    }

    public Double getDifference() {
        return difference;
    }

    public void setDifference(Double difference) {
        this.difference = difference;
    }
}
