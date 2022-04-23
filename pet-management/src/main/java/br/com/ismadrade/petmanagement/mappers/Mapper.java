package br.com.ismadrade.petmanagement.mappers;

public interface Mapper<D, E> {

    E toEntity(D dto);

}
