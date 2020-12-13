import io.reactivex.Observable
import java.lang.Exception

fun main(){
    rxFunction()
        .retry(1)
    .subscribe()
}

object Switch{
    var switch = false
}

fun rxFunction(): Observable<String> {
    if (Switch.switch){
        return Observable.create<String> { emitter ->
            emitter.onNext("1")
            emitter.onNext("2")
            emitter.onNext("3")
            emitter.onComplete()
        }.doOnNext { println(it)}
            .doOnError {
                println("onError correct")
                println(Switch.switch)
                Switch.switch = true }
    }
    else {
        return Observable.create<String> { emitter ->
            emitter.onError(throw Exception("Hello my friend!"))
            emitter.onNext("1")
            emitter.onNext("2")
            emitter.onNext("3")
            emitter.onComplete()
    }.doOnNext { println(it) }
            .doOnError {
                println("onError wrong")
                println(Switch.switch)
                Switch.switch = true }
    }
}
