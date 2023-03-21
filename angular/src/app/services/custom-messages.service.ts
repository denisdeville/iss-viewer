import { Injectable } from '@angular/core';
import { Message } from 'primeng/api';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomMessagesService {

  private onErrorSubject = new Subject<Message>();
  public onError$ = this.onErrorSubject.asObservable();

  constructor() { }

  public addError(summary: string, details: string) {
    this.onErrorSubject.next({summary: summary, detail: details, severity: 'error'});
  }
}
