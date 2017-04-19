#### 1. Tomcat 서버를 시작할 때 웹 애플리케이션이 초기화하는 과정을 설명하라.
* 서버가 시작되면 ServletContextListener가 listen하고 있다가 tomcat 컨테이너의 서블릿 컨텍스트가 초기화되고 나면 contextInitialized 메서드를 호출하게 되고 여기서 DB 초기화를 한다.
* 서블릿 컨텍스트가 초기화된 후에 컨테이너는 서블릿 클래스들을 로드하고 인스턴스화한다. 처음 만들어질 때 init 메서드가 불러지게 되는데 여기서 각 url은 컨트롤러로 맵핑시켜주는 request mapping의 초기화를 해준다.

#### 2. Tomcat 서버를 시작한 후 http://localhost:8080으로 접근시 호출 순서 및 흐름을 설명하라.
* 클라이언트가 서버에 요청을 하면 기다리고 있던 서버는 Dispatcher 서블릿을 서블릿 풀에서 가져온다.
* 서블릿은 요청의 접근 url을 확인하고 request mapping에서 url에 해당되는 컨트롤러 인스턴스를 가져와 컨트롤러에게 처리를 맡긴다.
* 컨트롤러는 적당한 처리를 해(현재 경우엔 HomeController가 "home.jsp" 파일 경로와 questionDao를 통해 가져온 질문 내용들을 ModelAndView로 리턴해준다) 반환 값을 Dispatcher 서블릿에게 넘겨준다
* Dispatcher 서블릿은 해당하는 뷰를 찾고, 같이 받은 모델의 정보로 뷰를 렌더링해서 클라이언트에게 보내준다.

#### 7. next.web.qna package의 ShowController는 멀티 쓰레드 상황에서 문제가 발생하는 이유에 대해 설명하라.
* Dispatcher 서블릿 인스턴스들은 하나의 컨트롤러를 공유해 사용하고 있다. 컨트롤러 내에 메서드만 존재할 경우 메서드의 로컬 변수는 각 서블릿 쓰레드에 독립적으로 스택으로 저장되기 때문에 이렇게 사용해도 멀티쓰레드 환경에서 thread-safe 하지만, 인스턴스 변수가 존재할 경우 모든 서블릿 인스턴스가 하나의 인스턴스 변수에 접근해 사용하기 때문에 thread-safe 하지 않다.
