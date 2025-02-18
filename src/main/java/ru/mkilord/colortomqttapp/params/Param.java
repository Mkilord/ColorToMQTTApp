package ru.mkilord.colortomqttapp.params;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

import static lombok.AccessLevel.PRIVATE;

@Getter
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AllArgsConstructor(access = PRIVATE)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public abstract class Param<T> {
    @EqualsAndHashCode.Include
    String key;
    @NonFinal
    @Setter
    T value;
    String desc;
    Verify verify;

    public boolean verify(String value) throws ParamException {
        if (Objects.isNull(verify)) return true;
        return verify.verify(value);
    }
}
