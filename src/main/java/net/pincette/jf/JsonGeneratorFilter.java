package net.pincette.jf;

import static javax.json.Json.createValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import javax.json.JsonException;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;

/**
 * A filter for <code>JsonGenerators</code>. You can use it as follows:
 *
 * <p>{@code new JsonGeneratorFilter()
 * .thenApply(new JsonGeneratorFilter())
 * ...
 * thenApply(new JsonGenerator())}</p>
 *
 * <p>The last one may be a plain <code>JsonGenerator</code>. The result of the entire expression
 * is the first filter.</p>
 *
 * <p>The value writers in this class call the variants with the <code>JsonValue</code> type.</p>
 *
 * @author Werner Donn\u00e9
 */
public class JsonGeneratorFilter implements JsonGenerator {
  private JsonGenerator next;

  public void close() {
    Optional.ofNullable(next).ifPresent(JsonGenerator::close);
  }

  public void flush() {
    Optional.ofNullable(next).ifPresent(JsonGenerator::flush);
  }

  public JsonGeneratorFilter thenApply(final JsonGenerator next) {
    if (this.next == null) {
      this.next = next;
    } else {
      if (this.next instanceof JsonGeneratorFilter) {
        ((JsonGeneratorFilter) this.next).thenApply(next);
      } else {
        throw new JsonException("Unsupported operation");
      }
    }

    return this;
  }

  public JsonGenerator write(final boolean value) {
    if (next != null) {
      write(value ? JsonValue.TRUE : JsonValue.FALSE);
    }

    return this;
  }

  public JsonGenerator write(final double value) {
    if (next != null) {
      write(createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final int value) {
    if (next != null) {
      write(createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final long value) {
    if (next != null) {
      write(createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String value) {
    if (next != null) {
      write(createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final BigDecimal value) {
    if (next != null) {
      write(createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final BigInteger value) {
    if (next != null) {
      write(createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final JsonValue value) {
    if (next != null) {
      next.write(value);
    }

    return this;
  }

  public JsonGenerator write(final String name, final boolean value) {
    if (next != null) {
      write(name, value ? JsonValue.TRUE : JsonValue.FALSE);
    }

    return this;
  }

  public JsonGenerator write(final String name, final double value) {
    if (next != null) {
      write(name, createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String name, final int value) {
    if (next != null) {
      write(name, createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String name, final long value) {
    if (next != null) {
      write(name, createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String name, final String value) {
    if (next != null) {
      write(name, createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String name, final BigDecimal value) {
    if (next != null) {
      write(name, createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String name, final BigInteger value) {
    if (next != null) {
      write(name, createValue(value));
    }

    return this;
  }

  public JsonGenerator write(final String name, final JsonValue value) {
    if (next != null) {
      next.write(name, value);
    }

    return this;
  }

  public JsonGenerator writeEnd() {
    if (next != null) {
      next.writeEnd();
    }

    return this;
  }

  public JsonGenerator writeKey(final String name) {
    if (next != null) {
      next.writeKey(name);
    }

    return this;
  }

  public JsonGenerator writeNull() {
    if (next != null) {
      next.writeNull();
    }

    return this;
  }

  public JsonGenerator writeNull(final String name) {
    if (next != null) {
      next.writeNull(name);
    }

    return this;
  }

  public JsonGenerator writeStartArray() {
    if (next != null) {
      next.writeStartArray();
    }

    return this;
  }

  public JsonGenerator writeStartArray(final String name) {
    if (next != null) {
      next.writeStartArray(name);
    }

    return this;
  }

  public JsonGenerator writeStartObject() {
    if (next != null) {
      next.writeStartObject();
    }

    return this;
  }

  public JsonGenerator writeStartObject(final String name) {
    if (next != null) {
      next.writeStartObject(name);
    }

    return this;
  }
}
