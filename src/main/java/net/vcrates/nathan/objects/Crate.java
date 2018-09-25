package net.vcrates.nathan.objects;

public class Crate {

    private int id;
    private String name;
    private String world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public Crate(int id, String name, String world, double x, double y, double z, float yaw, float pitch) {
        this.id = id;
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }
}
