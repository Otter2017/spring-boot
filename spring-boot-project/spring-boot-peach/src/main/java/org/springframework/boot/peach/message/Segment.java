package org.springframework.boot.peach.message;

public class Segment {
    static final String FIELD_SEPERATOR = "\\|";
    static final String NAME_SEPERATOR = "ф♮";
    private String name;
    private String[] fields;

    public Segment(String name, String fieldValues) {
        this.name = name;
        fields = fieldValues.split(FIELD_SEPERATOR);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int size() {
        if (fields == null)
            return 0;
        return fields.length;
    }

    public void setField(int index, String value) {
        fields[index] = value;
    }

    public String getFieldValue(int index) {
        return fields[index];
    }

    public static Segment parse(String segmentContent) {
        String[] segment = segmentContent.split(NAME_SEPERATOR);
        return new Segment(segment[0], segment[1]);
    }

    @Override
    public String toString() {
        StringBuffer segmentBuffer = new StringBuffer();
        segmentBuffer.append(name);
        segmentBuffer.append(NAME_SEPERATOR);
        for (int i = 0; i < fields.length; i++) {
            segmentBuffer.append(fields[i]);
            if (i < fields.length - 1)
                segmentBuffer.append(FIELD_SEPERATOR);
        }
        return segmentBuffer.toString();
    }
}
