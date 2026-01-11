activity_search.xml -> search_empty.xml, search_recent_archive.xml, search_recent_type.xml 합친 것
↓ activity_search.xml 구성 ↓
[ 뒤로가기 아이콘 ]

[ 최근 검색어 ]
└ if (검색기록 없음) → "검색한 기록이 없어요" 텍스트
└ else → ChipGroup

[ 검색 결과 ]
└ if (키워드 입력됨) → 카테고리별 텍스트 + 약 리스트 (CardView 등)

[ 검색창 (공통) ]

⭐약 리스트, 기관(소화기관, 위장기관 등) 리스트로 변경해야함
