package kr.co.upcoding.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.upcoding.vo.TestVO;

@Mapper
public interface TestMapper {
	int testInsert (TestVO testvo);
	int testDelete (TestVO testvo);
}
