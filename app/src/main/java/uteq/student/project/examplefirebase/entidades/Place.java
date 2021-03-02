package uteq.student.project.examplefirebase.entidades;

public class Place {
    private String name_place;
    private double latitud;
    private double longitud;

    public Place() {
    }

    public Place(String name_place, double latitud, double longitud) {
        this.name_place = name_place;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public String getName_place() {
        return name_place;
    }

    public void setName_place(String name_place) {
        this.name_place = name_place;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
