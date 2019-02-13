import '../webapp/css/custom.css';
 
import React from 'react';
import ReactDOM from 'react-dom';
 
class MainPage extends React.Component {
 
    render() {
        return (
        	<React.Fragment>
	        	<div className="main">좆메인 페이지</div>
	        	<form method="post" action="/testinsert">
	        		<input type="text" name="name" />
	        		<input type="text" name="nickname" />
	        		<button type="submit">ㄱ</button>
	        	</form>
	        	<br />
	        	<form method="post" action="/testdelete">
	        		<input type="text" name="name" />
	        		<button type="submit">ㄴ</button>
	        	</form>
        	</React.Fragment>
        );
    }
 
}
 
ReactDOM.render(<MainPage/>, document.getElementById('root'));