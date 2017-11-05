package dev.olog.shared.mapper;

public interface Mapper <From, To> {

    To map(From from);

}
