package simpleclient.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

public class JsonFile {
    private File file;
    private JsonObject json;

    public JsonFile(String name) {
        this(new File(""));
    }

    public JsonFile(File file) {
        this.file = file;
        load();
    }

    public void load() {
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) json = new JsonObject();
            else json = new JsonStreamParser(new FileReader(file)).next().getAsJsonObject();
        } catch (Exception e) {
            e.printStackTrace();
            json = new JsonObject();
        }
    }

    public void save() {
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            if (!file.exists()) file.createNewFile();
            Files.write(file.toPath(), json.toString().getBytes());
        } catch (Exception e) {e.printStackTrace();}
    }

    public boolean has(String key) {
        return json.has(key);
    }

    public void setDefault(String key, JsonElement value) {
        if (!has(key)) set(key, value);
    }

    public void setDefault(String key, Number value) {
        if (!has(key)) set(key, value);
    }

    public void setDefault(String key, String value) {
        if (!has(key)) set(key, value);
    }

    public void setDefault(String key, Boolean value) {
        if (!has(key)) set(key, value);
    }

    public void setDefault(String key, Character value) {
        if (!has(key)) set(key, value);
    }

    public void set(String key, JsonElement value) {
        json.add(key, value);
    }

    public void set(String key, Number value) {
        json.addProperty(key, value);
    }

    public void set(String key, String value) {
        json.addProperty(key, value);
    }

    public void set(String key, Boolean value) {
        json.addProperty(key, value);
    }

    public void set(String key, Character value) {
        json.addProperty(key, value);
    }

    public JsonElement get(String key) {
        return json.get(key);
    }

    public byte getByte(String key) {
        return json.get(key).getAsByte();
    }

    public int getInt(String key) {
        return json.get(key).getAsInt();
    }

    public short getShort(String key) {
        return json.get(key).getAsShort();
    }

    public JsonObject getObject(String key) {
        return json.get(key).getAsJsonObject();
    }

    public boolean getBoolean(String key) {
        return json.get(key).getAsBoolean();
    }

    public double getDouble(String key) {
        return json.get(key).getAsDouble();
    }

    public float getFloat(String key) {
        return json.get(key).getAsFloat();
    }

    public JsonArray getArray(String key) {
        return json.get(key).getAsJsonArray();
    }

    public long getLong(String key) {
        return json.get(key).getAsLong();
    }

    public String getString(String key) {
        return json.get(key).getAsString();
    }

    public JsonObject getJson() {
        return json;
    }

    public void setJson(JsonObject json) {
        this.json = json;
    }
}
