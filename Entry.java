package application;

public class Entry {

  private String id;
  private String date;
  private double weight;

  public Entry(String date, String id, double weight) {
    this.date = date;
    this.id = id;
    this.weight = weight;
  }

  public String getID() {
    return id;
  }

  public void setID(String id) {
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public String toString() {

    return date + ": " + id + ", " + weight;
  }
}
