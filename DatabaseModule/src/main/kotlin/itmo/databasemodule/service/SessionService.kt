package itmo.databasemodule.service

import itmo.databasemodule.domain.session.Session
import itmo.databasemodule.dto.SessionDto
import itmo.databasemodule.dto.toDto
import itmo.databasemodule.repository.SessionRepository
import itmo.databasemodule.search.SearchCriteriaDto
import itmo.databasemodule.search.SearchMapper
import itmo.databasemodule.user.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SessionService(
    private val repository: SessionRepository,
    private val userService: UserService,
    private val searchMapper: SearchMapper<Session>,
) {
    @Transactional(readOnly = true)
    fun findAllMy(pageable: Pageable): Page<SessionDto> =
        repository.findAllByUser(userService.getCurrentUser(), pageable).map { it.toDto() }

    @Transactional(readOnly = true)
    fun search(criteria: SearchCriteriaDto?, pageable: Pageable): Page<SessionDto> =
        repository.findAll(searchMapper.map(criteria), pageable).map { it.toDto() }
}