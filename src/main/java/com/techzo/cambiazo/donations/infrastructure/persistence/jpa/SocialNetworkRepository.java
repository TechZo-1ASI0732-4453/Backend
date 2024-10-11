package com.techzo.cambiazo.donations.infrastructure.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.techzo.cambiazo.donations.domain.model.aggregates.Ong;
import com.techzo.cambiazo.donations.domain.model.aggregates.SocialNetwork;
import com.techzo.cambiazo.donations.domain.model.valueobjects.SocialNetworkName;
import com.techzo.cambiazo.donations.domain.model.valueobjects.SocialNetworkUrl;

import java.util.List;
import java.util.Optional;

@Repository
public interface SocialNetworkRepository extends JpaRepository<SocialNetwork, Long>{

    Optional<SocialNetwork>findByNameAndUrl(SocialNetworkName name, SocialNetworkUrl url);

    List<SocialNetwork> findByOngId(Ong id);
}
