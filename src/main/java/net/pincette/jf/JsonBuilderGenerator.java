package net.pincette.jf;

import static javax.json.Json.createArrayBuilder;
import static javax.json.Json.createObjectBuilder;
import static javax.json.Json.createValue;
import static net.pincette.util.Pair.pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.function.Consumer;
import javax.json.JsonArrayBuilder;
import javax.json.JsonException;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import net.pincette.util.Pair;

/**
 * Accumulates a JSON stream in a given JSON builder.
 *
 * @author Werner Donn\u00e9
 */
public class JsonBuilderGenerator implements JsonGenerator {
  private Object builder;
  private Deque<Pair<String, Object>> builders = new ArrayDeque<>();
  private String lastName;

  public JsonBuilderGenerator() {}

  public JsonBuilderGenerator(final JsonObjectBuilder builder) {
    builders.push(pair(null, builder));
  }

  public JsonBuilderGenerator(final JsonArrayBuilder builder) {
    builders.push(pair(null, builder));
  }

  private static void add(final Object parent, final Pair<String, Object> builder) {
    if (parent instanceof JsonObjectBuilder) {
      if (builder.first != null) {
        ((JsonObjectBuilder) parent).add(builder.first, build(builder.second));
      }
    } else {
      ((JsonArrayBuilder) parent).add(build(builder.second));
    }
  }

  private static JsonStructure build(final Object builder) {
    return builder instanceof JsonObjectBuilder
        ? ((JsonObjectBuilder) builder).build()
        : ((JsonArrayBuilder) builder).build();
  }

  private Optional<JsonObjectBuilder> asObjectBuilder() {
    return Optional.ofNullable(builders.peek()).map(p -> (JsonObjectBuilder) p.second);
  }

  private Optional<JsonArrayBuilder> asArrayBuilder() {
    return Optional.ofNullable(builders.peek()).map(p -> (JsonArrayBuilder) p.second);
  }

  /**
   * Use this method only when no builder was passed through a constructor, because in that case
   * everything is added to that builder.
   *
   * @return The created JSON structure.
   */
  public JsonStructure build() {
    if (builder == null) {
      throw new IllegalStateException("Object or array is not complete");
    }

    return build(builder);
  }

  private void checkNoLastName() {
    if (lastName != null) {
      throw new JsonException("writeKey was called without following value");
    }
  }

  public void close() {
    // Nothing to close.
  }

  public void flush() {
    // Nothing to flush.
  }

  public JsonGenerator write(final boolean value) {
    write(value ? JsonValue.TRUE : JsonValue.FALSE);

    return this;
  }

  public JsonGenerator write(final double value) {
    write(createValue(value));

    return this;
  }

  public JsonGenerator write(final int value) {
    write(createValue(value));

    return this;
  }

  public JsonGenerator write(final long value) {
    write(createValue(value));

    return this;
  }

  public JsonGenerator write(final String value) {
    write(createValue(value));

    return this;
  }

  public JsonGenerator write(final BigDecimal value) {
    write(createValue(value));

    return this;
  }

  public JsonGenerator write(final BigInteger value) {
    write(createValue(value));

    return this;
  }

  public JsonGenerator write(final JsonValue value) {
    writeAnonymous(
        name -> asObjectBuilder().ifPresent(b -> b.add(name, value)),
        () -> asArrayBuilder().ifPresent(b -> b.add(value)));

    return this;
  }

  public JsonGenerator write(final String name, final boolean value) {
    write(name, value ? JsonValue.TRUE : JsonValue.FALSE);

    return this;
  }

  public JsonGenerator write(final String name, final double value) {
    write(name, createValue(value));

    return this;
  }

  public JsonGenerator write(final String name, final int value) {
    write(name, createValue(value));

    return this;
  }

  public JsonGenerator write(final String name, final long value) {
    write(name, createValue(value));

    return this;
  }

  public JsonGenerator write(final String name, final String value) {
    write(name, createValue(value));

    return this;
  }

  public JsonGenerator write(final String name, final BigDecimal value) {
    write(name, createValue(value));

    return this;
  }

  public JsonGenerator write(final String name, final BigInteger value) {
    write(name, createValue(value));

    return this;
  }

  public JsonGenerator write(final String name, final JsonValue value) {
    checkNoLastName();
    asObjectBuilder().ifPresent(b -> b.add(name, value));

    return this;
  }

  private void writeAnonymous(final Consumer<String> objectBuilder, final Runnable arrayBuilder) {
    if (lastName != null) {
      objectBuilder.accept(lastName);
      lastName = null;
    } else {
      arrayBuilder.run();
    }
  }

  public JsonGenerator writeEnd() {
    final Pair<String, Object> bldr = builders.pop();

    Optional.ofNullable(builders.peek()).map(p -> p.second).ifPresent(b -> add(b, bldr));

    if (builders.isEmpty()) {
      builder = bldr.second;
    }

    return this;
  }

  public JsonGenerator writeKey(final String name) {
    lastName = name;

    return this;
  }

  public JsonGenerator writeNull() {
    write(JsonValue.NULL);

    return this;
  }

  public JsonGenerator writeNull(final String name) {
    write(name, JsonValue.NULL);

    return this;
  }

  public JsonGenerator writeStartArray() {
    builders.push(pair(lastName, createArrayBuilder()));
    lastName = null;

    return this;
  }

  public JsonGenerator writeStartArray(final String name) {
    checkNoLastName();
    builders.push(pair(name, createArrayBuilder()));

    return this;
  }

  public JsonGenerator writeStartObject() {
    builders.push(pair(lastName, createObjectBuilder()));
    lastName = null;

    return this;
  }

  public JsonGenerator writeStartObject(final String name) {
    checkNoLastName();
    builders.push(pair(name, createObjectBuilder()));

    return this;
  }
}
