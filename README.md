# 12th-BE
12th 백엔드 서버입니다

## 검색 API 성능 테스트

실행 중인 서버를 대상으로 플레이어 검색과 팀 검색에 부하를 발생시킵니다.

```bash
./gradlew gatlingRun --simulation com.example.be12th.performance.SearchSimulation --non-interactive
```

기본 설정은 `http://localhost:8080`에 10초 동안 부하를 증가시킨 뒤 20초 동안
플레이어 검색 0.5 RPS, 팀 검색 0.5 RPS를 유지합니다. 다음 환경변수로 조절할 수 있습니다.

| 환경변수 | 기본값 | 설명 |
| --- | ---: | --- |
| `BASE_URL` | `http://localhost:8080` | 테스트 대상 서버 |
| `SEASON` | `2025` | 검색 시즌 |
| `PLAYER_RPS` | `0.5` | 플레이어 검색 초당 요청 수 |
| `TEAM_RPS` | `0.5` | 팀 검색 초당 요청 수 |
| `RAMP_SECONDS` | `10` | 목표 RPS까지 증가하는 시간 |
| `DURATION_SECONDS` | `20` | 목표 RPS 유지 시간 |
| `PLAYER_KEYWORDS` | `Gustav,Seung,Jin-su` | 쉼표로 구분한 선수 검색어 |
| `TEAM_KEYWORDS` | `서울,울산,전북` | 쉼표로 구분한 팀 검색어 |
| `MAX_FAILED_PERCENT` | `1.0` | 허용 실패율(%) |
| `MAX_P95_MILLIS` | `2000` | 허용 p95 응답시간(ms) |

예를 들어 각 API를 5 RPS로 1분 동안 테스트하려면 다음과 같이 실행합니다.

```bash
PLAYER_RPS=5 TEAM_RPS=5 RAMP_SECONDS=30 DURATION_SECONDS=60 \
  ./gradlew gatlingRun --simulation com.example.be12th.performance.SearchSimulation --non-interactive
```

검색 서비스는 API-Football을 호출하므로 높은 RPS로 실행하면 외부 API 쿼터와
rate limit을 빠르게 소진할 수 있습니다. 운영 서버에 실행하기 전에 대상 URL과
외부 API 사용 한도를 확인해야 합니다. HTML 리포트는 실행 후
`build/reports/gatling` 아래에 생성됩니다.
