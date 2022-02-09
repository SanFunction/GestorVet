package com.capgemini.controller;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import com.fasterxml.jackson.annotation.ObjectIdResolver;

import javax.persistence.EntityManager;

public class EntityIdResolver implements ObjectIdResolver {
	
	private EntityManager entityManager;

	public EntityIdResolver(final EntityManager entityManager) {
		this.entityManager=entityManager;
	}
	
	@Override
	public void bindItem(final ObjectIdGenerator.IdKey id, final Object pojo) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object resolveId(final ObjectIdGenerator.IdKey id) {
		// TODO Auto-generated method stub
		return this.entityManager.find(id.scope, id.key);
	}

	@Override
	public ObjectIdResolver newForDeserialization(final Object context) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean canUseFor(final ObjectIdResolver resolverType) {
		// TODO Auto-generated method stub
		return false;
	}

}
