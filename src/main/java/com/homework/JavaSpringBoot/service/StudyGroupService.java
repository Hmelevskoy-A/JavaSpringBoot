package com.homework.JavaSpringBoot.service;




import com.homework.JavaSpringBoot.model.Student;
import com.homework.JavaSpringBoot.model.StudyGroup;
import com.homework.JavaSpringBoot.repository.StudyGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("StudyGroupService")
public class StudyGroupService {

	@Autowired
	private StudyGroupRepository groupRepository;


	public StudyGroup getGroup(Long id) {
		return groupRepository.findById(id).get();
	}


	public Long saveGroup(StudyGroup gr) {
		return groupRepository.save(gr).getId();
	}


	public List<StudyGroup> listAllGroups() {
		return groupRepository.findAll();
	}


	public void update(Long id, StudyGroup gr) {

		StudyGroup stEntity = groupRepository.findById(id).get();
		if (stEntity != null) {
			stEntity.setName(gr.getName());
			stEntity.setEmail(gr.getEmail());
			stEntity.setStudentList(gr.getStudentList());
			groupRepository.save(stEntity);
		}
	}


	public void delete(Long id) {
		StudyGroup stEntity = groupRepository.findById(id).get();
		if (stEntity != null) {
			groupRepository.delete(stEntity);
		}
	}


	public boolean isGroupUnique(Long id) {
		StudyGroup group = groupRepository.findById(id).get();
		return (group == null || (id != null & !id.equals(group.getId())));
	}

	//Task46:
	public List<Student> getStudentsFromGroupByName(String name) {
		return groupRepository.findByName(name).getStudentList();
	}

}
