import { Injectable } from '@angular/core';
import Feature from 'ol/Feature';
import { Message } from 'primeng/api';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CustomMessagesService {

  private onErrorSubject = new Subject<Message>();
  public onError$ = this.onErrorSubject.asObservable();

  private onAlertSubject = new Subject<Message>();
  public onAlert$ = this.onAlertSubject.asObservable();

  constructor() { }

  public addError(summary: string, details: string) {
    this.onErrorSubject.next({summary: summary, detail: details, severity: 'error'});
  }

  public handleError(error: any) {
   if (error.name != null && error.message != null) {
    this.addError(error.name, error.message)
   } else {
    this.addError(error.error.errorCode, error.error.message);
   }
  }

  public addAlert(summary: string, details: string, feature: Feature) {
    this.onAlertSubject.next({summary: summary, detail: details, severity: 'success', data: feature});
  }
}
